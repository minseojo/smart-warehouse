import { Truck } from './Truck.js';

export class TruckManager {
    constructor(scene) {
        this.scene = scene;
        this.trucks = [];
    }

    createTruck({ id, position, boxCount }) {
        const truck = new Truck(this.scene, { id, position, boxCount });
        this.trucks.push(truck);
        return truck;
    }

    releaseTruck(truck) {
        this.trucks.pop(truck);
    }

    getTrucksByState(state) {
        return this.trucks.filter(truck => truck.state === state);
    }
}
