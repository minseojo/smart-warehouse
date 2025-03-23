import { CargoBox } from '../box/CargoBox.js';
import { TruckState } from './TruckState.js';

export class Truck {
    static DOOR_WIDTH = 0.5;
    static PARKING_OFFSET = 50;

    constructor(scene, {
        id,
        position,
        size = [20, 10, 15],
        color = 0x546E7A,
        boxCount = 5
    } = {}) {
        this.scene = scene;
        this.id = id;
        this.position = position;
        this.size = size;
        this.color = color;
        this.boxCount = boxCount;
        this.state = TruckState.IDLE;
        this.boxes = [];
        this.unloadingBoxes = [];

        this.mesh = new THREE.Mesh(
            new THREE.BoxGeometry(...size),
            new THREE.MeshPhongMaterial({ color })
        );
        this.mesh.userData = { id, type: 'truck' };

        const y = position.y + size[1] / 2;
        const z = position.z;
        const x = position.x - Truck.PARKING_OFFSET;

        this.mesh.position.set(x, y, z);

        this.createDoor();

        this.scene.add(this.mesh);
    }

    setState(newState) {
        this.state = newState;
    }

    setUnloadCallback(onUnloadComplete) {
        this.onUnloadComplete = onUnloadComplete;
    }

    createDoor() {
        const [truckWidth, truckHeight, truckDepth] = this.size;
        const y = this.position.y + truckHeight / 2;
        const z = this.position.z;
        const x = this.mesh.position.x + truckWidth / 2 - Truck.DOOR_WIDTH / 2;

        this.door = new THREE.Mesh(
            new THREE.BoxGeometry(Truck.DOOR_WIDTH, truckHeight, truckDepth),
            new THREE.MeshPhongMaterial({ color: 0x37474F })
        );
        this.door.position.set(x, y, z);
        this.scene.add(this.door);
    }

    start() {
        this.targetParkX = this.position.x;
        this.setState(TruckState.IDLE);
    }

    startUnloading({ boxSpacing = CargoBox.BOX_SIZE.x + 1, delay = 100, onDrop = () => {}, onComplete = () => {} } = {}) {
        const zoneWidth = 100;
        const boxesPerZ = 5;
        const boxesPerX = Math.floor(zoneWidth / boxSpacing);
        const boxesPerLayer = boxesPerZ * boxesPerX;

        let dropped = 0;
        this.unloadingBoxes = [];

        const interval = setInterval(() => {
            if (dropped >= this.boxCount) {
                clearInterval(interval);
                onComplete();
                return;
            }

            const z = dropped % boxesPerZ;
            const x = Math.floor(dropped / boxesPerZ) % boxesPerX;
            const y = Math.floor(dropped / boxesPerLayer);

            const startX = this.mesh.position.x + 20 + x * boxSpacing;
            const startZ = this.mesh.position.z - 8 + z * boxSpacing;
            const startY = this.mesh.position.y - this.size[1] / 2 + 2 + y * (CargoBox.BOX_SIZE.y + 1);

            const targetPos = new THREE.Vector3(startX, startY, startZ);
            const startPos = targetPos.clone().sub(new THREE.Vector3(1, 0, 0));

            const box = new CargoBox(this.scene, startPos);
            this.unloadingBoxes.push({ box, target: targetPos });

            onDrop(box);
            dropped++;
        }, delay);
    }

    update() {
        switch (this.state) {
            case TruckState.IDLE: {
                // 트럭이 지정 위치까지 도달하면 PARKING 상태로 전환
                this.mesh.position.x += 0.5;
                this.door.position.x += 0.5;

                if (this.mesh.position.x >= this.position.x) {
                    this.mesh.position.x = this.position.x;
                    this.door.position.x = this.mesh.position.x + this.size[0] / 2 - Truck.DOOR_WIDTH / 2;

                    this.setState(TruckState.PARKING);
                }
                break;
            }

            case TruckState.PARKING: {
                this.setState(TruckState.UNLOADING);
                this.startUnloading({
                    onComplete: () => {
                        this.setState(TruckState.EXITING);
                    }
                });
                break;
            }

            case TruckState.EXITING: {
                this.mesh.position.x -= 0.5;
                this.door.position.x -= 0.5;

                if (this.mesh.position.x <= this.position.x - Truck.PARKING_OFFSET) {
                    this.setState(TruckState.IDLE);
                    if (this.onUnloadComplete) this.onUnloadComplete(this);
                    this.remove();
                }
                break;
            }
        }

        this.handleUnloadingBoxes();
    }

    handleUnloadingBoxes() {
        if (this.unloadingBoxes && this.unloadingBoxes.length > 0) {
            this.unloadingBoxes = this.unloadingBoxes.filter(({ box, target }) => {
                const direction = target.clone().sub(box.mesh.position);
                const distance = direction.length();

                if (distance < 0.3) {
                    box.mesh.position.copy(target);
                    this.boxes.push(box);
                    return false;
                }

                direction.normalize();
                box.mesh.position.add(direction.multiplyScalar(0.3));
                return true;
            });
        }

        this.boxes.forEach(box => box.update());
    }

    remove() {
        this.scene.remove(this.mesh);
        if (this.door) this.scene.remove(this.door);
        this.boxes.forEach(box => box.remove());
    }
}