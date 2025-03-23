let chargerIdCounter = 1;

export class Charger {
    constructor(scene, { id = null, name = "Unnamed Charger", position, size = { x: 10, y: 5, z: 5 }, color = 0x00ACC1 }, labelManager) {
        this.id = id ?? `charger-${chargerIdCounter++}`;
        this.name = name;
        this.position = position;
        this.size = size;
        this.defaultColor = color;
        this.labelManager = labelManager;

        this.batteryLevel = 100;
        this.status = "idle";
        this.queue = [];

        this.material = new THREE.MeshPhongMaterial({ color: this.defaultColor });
        this.mesh = new THREE.Mesh(
            new THREE.BoxGeometry(size.x, size.y, size.z),
            this.material
        );
        this.mesh.position.copy(position);
        this.mesh.userData = { id: this.id, type: "charger", charger: this };

        this.batteryBar = this.createBatteryBar();
        this.mesh.add(this.batteryBar);

        // 라벨 등록 (자동 위치 추적 & 텍스트 갱신)
        this.queueLabel = this.labelManager.addLabel({
            text: `${this.name}\n(대기 0명)`,
            target: this.mesh,
            getText: () => `${this.name}\n(대기 ${this.queue.length}명)`
        });

        scene.add(this.mesh);
    }

    static create(scene, config = {}, labelManager) {
        return new Charger(scene, config, labelManager);
    }

    createBatteryBar() {
        const barHeight = 0.3;
        const bar = new THREE.Mesh(
            new THREE.BoxGeometry(this.size.x * 0.9, barHeight, 0.5),
            new THREE.MeshPhongMaterial({ color: 0x00E676 })
        );
        bar.position.set(0, this.size.y / 2 + barHeight / 2 + 0.1, 0);
        return bar;
    }

    updateBatteryBar() {
        const scale = Math.max(0.01, this.batteryLevel / 100);
        this.batteryBar.scale.x = scale;
        this.batteryBar.material.color.setHex(
            scale < 0.2 ? 0xff3d00 :
                scale < 0.5 ? 0xffeb3b : 0x00E676
        );
    }

    startCharging(robotId) {
        if (this.status === "charging") {
            this.queue.push(robotId);
            return;
        }
        this.status = "charging";
        this.material.color.setHex(0xFFD54F); // charging yellow
        this.consumeCharge(robotId);
    }

    async consumeCharge(robotId) {
        const interval = setInterval(() => {
            this.batteryLevel += 1;
            this.updateBatteryBar();

            if (this.batteryLevel >= 100) {
                clearInterval(interval);
                this.stopCharging();

                if (this.queue.length > 0) {
                    const next = this.queue.shift();
                    this.startCharging(next);
                }
            }
        }, 100);
    }

    stopCharging() {
        this.status = "idle";
        this.material.color.setHex(this.defaultColor);
    }

    updateBattery(amount) {
        this.batteryLevel = Math.max(0, Math.min(100, this.batteryLevel + amount));
        this.updateBatteryBar();
    }

    moveTo(newPosition) {
        this.position.copy(newPosition);
        this.mesh.position.copy(newPosition);
    }

    createQueueLabel() {
        const div = document.createElement('div');
        div.style.position = 'absolute';
        div.style.backgroundColor = '#ffffffcc';
        div.style.padding = '4px 8px';
        div.style.borderRadius = '4px';
        div.style.fontSize = '12px';
        div.style.fontWeight = 'bold';
        div.style.color = '#000';
        div.style.pointerEvents = 'none';
        div.innerText = `${this.name}\n(대기 0명)`;

        this.labelElement = div;
        document.body.appendChild(div);
    }

    updateQueueLabel(camera) {
        if (!this.labelElement) return;

        // 위치 변환
        const vector = this.mesh.position.clone().project(camera);
        const halfWidth = window.innerWidth / 2;
        const halfHeight = window.innerHeight / 2;

        this.labelElement.style.left = `${vector.x * halfWidth + halfWidth - 35}px`;
        this.labelElement.style.top = `${-vector.y * halfHeight + halfHeight - 55}px`;

        this.labelElement.innerText = `${this.name}\n(대기 ${this.queue.length}명)`;
    }


}
