// robots-gui.js
import GUI from 'lil-gui';

export class RobotsGUI {
    constructor(robots) {
        this.robots = robots;  // 로봇 리스트

        this.params = {
            selectedRobotId: robots[0]?.id || '',
            speed: 1,
            state: robots[0]?.state || 'Idle',  // 상태 표시
            requestCharging: () => this.requestCharging(),
            togglePause: () => this.togglePause(),
        };

        this.gui = new GUI();
        this.createGUI();
    }

    createGUI() {
        // 로봇 선택
        const robotIds = this.robots.map(r => r.id);
        this.gui.add(this.params, 'selectedRobotId', robotIds)
            .name('Select Robot')
            .onChange(() => {
                const robot = this.getSelectedRobot();
                this.params.speed = robot.speed || 1;
                this.params.state = robot.state || 'Idle';
            });

        // 로봇 속도 조정
        this.gui.add(this.params, 'speed', 0.1, 3, 0.1)
            .name('Speed')
            .onChange(value => {
                const robot = this.getSelectedRobot();
                if (robot) robot.setSpeed(value);
            });

        // 로봇 상태 표시 (읽기 전용)
        this.gui.add(this.params, 'state')
            .name('State')
            .listen();

        // 충전 요청 버튼
        this.gui.add(this.params, 'requestCharging')
            .name('Request Charging');

        // 일시정지 / 재개
        this.gui.add(this.params, 'togglePause')
            .name('Pause / Resume');
    }

    getSelectedRobot() {
        return this.robots.find(r => r.id === this.params.selectedRobotId);
    }

    requestCharging() {
        const robot = this.getSelectedRobot();
        if (robot) {
            console.log(`[${robot.id}] Charging Requested`);
            robot.requestCharging();
            this.params.state = robot.state;  // 상태 업데이트
        }
    }

    togglePause() {
        const robot = this.getSelectedRobot();
        if (robot) {
            console.log(`[${robot.id}] Toggle Pause`);
            robot.togglePause();
            this.params.state = robot.state;  // 상태 업데이트
        }
    }

    // 외부에서 로봇 상태가 바뀔 때 호출하면 GUI 갱신됨
    updateRobotState(robotId, newState) {
        const robot = this.robots.find(r => r.id === robotId);
        if (robot) {
            robot.state = newState;
            if (robot.id === this.params.selectedRobotId) {
                this.params.state = newState;
            }
        }
    }
}
