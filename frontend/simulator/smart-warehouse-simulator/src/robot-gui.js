// robot-gui.js
import GUI from 'lil-gui';

export class RobotGUI {
    constructor(robot) {
        this.robot = robot;

        this.params = {
            robotId: robot.id || 'Robot-1',
            speed: 1,
            requestCharging: () => this.requestCharging(),
            togglePause: () => this.togglePause(),
        };

        this.gui = new GUI();
        this.createGUI();
    }

    createGUI() {
        this.gui.add(this.params, 'robotId').name('Robot ID').listen();

        this.gui.add(this.params, 'speed', 0.1, 3, 0.1)
            .name('Speed')
            .onChange(value => {
                this.robot.setSpeed(value);
            });

        this.gui.add(this.params, 'requestCharging')
            .name('Request Charging');

        this.gui.add(this.params, 'togglePause')
            .name('Pause / Resume');
    }

    // 충전 요청
    requestCharging() {
        console.log(`[${this.params.robotId}] Charging Request`);
        this.robot.requestCharging();
    }

    // 일시정지/재개
    togglePause() {
        console.log(`[${this.params.robotId}] Toggle Pause`);
        this.robot.togglePause();
    }
}
