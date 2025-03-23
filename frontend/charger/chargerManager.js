export class ChargerManager {
    constructor(labelManager) {
        this.chargers = [];
        this.labelManager = labelManager;
    }

    registerCharger(charger) {
        this.chargers.push(charger);
    }

    requestCharge(robotId) {
        // 간단히 가장 짧은 큐를 가진 charger에 등록
        const target = this.chargers.reduce((prev, curr) => {
            return (prev.queue.length <= curr.queue.length) ? prev : curr;
        });
        target.startCharging(robotId);
    }

    updateLabels(camera) {
        this.chargers.forEach(c => c.updateQueueLabel(camera));
    }
}
