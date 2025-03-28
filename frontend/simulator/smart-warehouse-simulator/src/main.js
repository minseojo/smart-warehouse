// ✅ 스마트 물류창고 시뮬레이터 - 서버 연동 + 기존 모듈 연계 완전판

import * as THREE from 'three';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls';
import { createFloorZones } from './zone/zone.js';
import { LabelManager } from './utils/labelManager';

// --- Interface ---
class SimulationData {
    /** @type {RobotData[]} */ robots = [];
    /** @type {string[]} */ queue = [];
}

class RobotData {
    /** @type {string} */ id;
    /** @type {'Idle' | 'Moving' | 'Charging' | 'Waiting'} */ state;
    /** @type {{x: number, y: number}} */ position;
    /** @type {number} */ speed;
    /** @type {number} */ battery;
}

// --- Visualizer ---
class Visualizer {
    constructor() {
        // Scene Setup
        this.scene = new THREE.Scene();
        this.camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
        this.renderer = new THREE.WebGLRenderer({ canvas: document.getElementById('three-canvas') });
        this.renderer.setSize(window.innerWidth, window.innerHeight);

        // Controls
        this.controls = new OrbitControls(this.camera, this.renderer.domElement);
        this.controls.enableDamping = true;

        // Label Manager
        this.labelManager = new LabelManager(this.camera);

        // Floor Zones
        createFloorZones(this.scene, this.camera, this.labelManager);

        // Robot Mesh & Label
        this.robotMeshes = {};  // { [id]: Mesh }
        this.robotLabels = {};  // { [id]: HTMLDivElement }

        // Camera
        this.camera.position.set(0, 50, 50);
        this.camera.lookAt(0, 0, 0);

        // Animate
        this.animate();
    }

    update(simulationData) {
        this.updateRobotList(simulationData.robots);
        this.updateQueue(simulationData.queue);
        this.update3DScene(simulationData);
        this.labelManager.updateAll();
    }

    updateRobotList(robots) {
        const list = document.getElementById('robot-items');
        list.innerHTML = '';
        robots.forEach(r => {
            const li = document.createElement('li');
            li.textContent = `${r.id} (${r.state}) Battery: ${r.battery}%`;
            list.appendChild(li);
        });
    }

    updateQueue(queue) {
        const q = document.getElementById('queue-items');
        q.innerHTML = '';
        queue.forEach(id => {
            const li = document.createElement('li');
            li.textContent = id;
            q.appendChild(li);
        });
    }

    update3DScene(data) {
        data.robots.forEach((robot, idx) => {
            // Mesh 생성
            if (!this.robotMeshes[robot.id]) {
                const geometry = new THREE.BoxGeometry(1, 1, 1);
                const material = new THREE.MeshStandardMaterial();
                const mesh = new THREE.Mesh(geometry, material);
                this.scene.add(mesh);
                this.robotMeshes[robot.id] = mesh;

                // Label 생성 (기존 LabelManager 사용)
                const label = this.labelManager.addLabel({
                    text: robot.id,
                    target: mesh,
                    getText: () => `${robot.id} (${robot.state})`
                });
                this.robotLabels[robot.id] = label;
            }

            // Queue에 있으면 대기열 위치로
            const mesh = this.robotMeshes[robot.id];
            if (data.queue.includes(robot.id)) {
                const qIndex = data.queue.indexOf(robot.id);
                mesh.position.set(-20 + qIndex * 2, 0.5, -20);
            } else {
                mesh.position.set(robot.position.x, 0.5, robot.position.y);
            }

            // 색상
            mesh.material.color.set(this.getColorByState(robot.state));
        });
    }

    getColorByState(state) {
        switch (state) {
            case 'Idle': return 'gray';
            case 'Moving': return 'blue';
            case 'Charging': return 'yellow';
            case 'Waiting': return 'orange';
            default: return 'white';
        }
    }

    animate() {
        requestAnimationFrame(() => this.animate());
        this.controls.update();
        this.renderer.render(this.scene, this.camera);
    }
}

// --- Real-time Data Receiver ---
const visualizer = new Visualizer();

function receiveFromServer(data) {
    visualizer.update(data);
}

// 예시: 서버에서 아래처럼 호출한다고 가정
// receiveFromServer(simulationData)

export { receiveFromServer }
