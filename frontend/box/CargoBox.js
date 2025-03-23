let boxIdCounter = 1;

export class CargoBox {
    static BOX_SIZE = { x: 5.5, y: 4, z: 5.5 };
    static BOX_GAP = 1;
    static DROP_HEIGHT = 20;
    static DROP_SPEED = 0.6;
    

    constructor(scene, dropPosition, color = 0xFFB74D) {
        this.id = `box-${boxIdCounter++}`;
        this.scene = scene;
        this.falling = true;

        const { x, y, z } = CargoBox.BOX_SIZE;

        this.mesh = new THREE.Mesh(
            new THREE.BoxGeometry(x, y, z),
            new THREE.MeshStandardMaterial({ color })
        );

        this.targetY = dropPosition.y;
        this.mesh.position.set(dropPosition.x, dropPosition.y + CargoBox.DROP_HEIGHT, dropPosition.z);

        scene.add(this.mesh);
    }

    update() {
        if (!this.falling) return;

        this.mesh.position.y -= CargoBox.DROP_SPEED;

        if (this.mesh.position.y <= this.targetY) {
            this.mesh.position.y = this.targetY;
            this.falling = false;
        }
    }

    remove() {
        this.scene.remove(this.mesh);
    }
}
