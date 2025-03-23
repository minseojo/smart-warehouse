export class InboundZoneManager {
    constructor(scene, capacity = 1) {
        this.scene = scene;
        this.capacity = capacity;
        this.queue = [];
        this.activeTrucks = [];
    }

    requestEntry(truck) {
        if (this.activeTrucks.length < this.capacity) {
            this.activeTrucks.push(truck);
    
            truck.setUnloadCallback(() => {
                this.removeTruck(truck);
            });
    
            truck.start();
        } else {
            this.queue.push(truck);
        }
    }    

    removeTruck(truck) {
        this.activeTrucks = this.activeTrucks.filter(t => t !== truck);
        this.scene.remove(truck.mesh);
        truck.boxes.forEach(box => box.remove());
    
        if (this.onTruckExit) this.onTruckExit(truck);
    
        if (this.queue.length > 0) {
            const nextTruck = this.queue.shift();
            this.requestEntry(nextTruck);
        }
    }     

    update() {
        this.activeTrucks.forEach(truck => truck.update());
    }
}