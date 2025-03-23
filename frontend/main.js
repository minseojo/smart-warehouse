// main.js
import { createWalls } from './wall.js';
import { createRobot, robotPath } from './robot.js';
import { createRacks } from './rack.js';
import { createFloorZones } from './floorzone.js';
import { Charger } from './charger/charger.js';
import { ChargerManager } from './charger/chargerManager.js';
import { LabelManager } from './utils/LabelManager.js';
import { InboundZoneManager } from './InboundZoneManager.js';
import { TruckManager } from './TruckManager.js';

// 초기 설정
const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
const renderer = new THREE.WebGLRenderer({ antialias: true });
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

// 카메라 위치 및 조명
camera.position.set(0, 150, 0);
camera.lookAt(0, 0, 0);
scene.add(new THREE.AmbientLight(0xffffff, 0.8));
const light = new THREE.DirectionalLight(0xffffff, 0.8);
light.position.set(10, 20, 10);
scene.add(light);

// 바닥
const floor = new THREE.Mesh(
    new THREE.PlaneGeometry(400, 200),
    new THREE.MeshStandardMaterial({ color: 0xECEFF1, metalness: 0.1, roughness: 0.8 })
);
floor.rotation.x = -Math.PI / 2;
scene.add(floor);

// 벽, 존, 선반, 로봇
createWalls(scene);
const labelManager = new LabelManager(camera);
createFloorZones(scene, camera, labelManager);

// const zoneA = createRacks(scene, { rows: 2, columns: 4, startX: -140, startZ: -90, rackSpacingX: 15, rackSpacingZ: 15, rackGroupId: 'ZONE-A', category: '전자부품', rackWidth: 8, rackHeight: 12, rackDepth: 5 });
// const zoneB = createRacks(scene, { rows: 3, columns: 5, startX: -140, startZ: 60, rackSpacingX: 15, rackSpacingZ: 15, rackGroupId: 'ZONE-C', category: '저장소', rackWidth: 8, rackHeight: 12, rackDepth: 5 });

const zoneIn = createRacks(scene, { rows: 6, columns: 9, startX: 60, startZ: 10, rackSpacingX: 15, rackSpacingZ: 15, rackGroupId: 'ZONE-B', category: '생활용품', rackWidth: 8, rackHeight: 12, rackDepth: 5 });
const zoneOut = createRacks(scene, { rows: 6, columns: 9, startX: 60, startZ: -90, rackSpacingX: 15, rackSpacingZ: 15, rackGroupId: 'ZONE-B', category: '생활용품', rackWidth: 8, rackHeight: 12, rackDepth: 5 });
const robot = createRobot(scene);
let currentTargetIndex = 0;

// 충전소
const chargerManager = new ChargerManager(labelManager);
[
    { id: 'north-west', name: '북서쪽 충전소', pos: [-25, 2.5, -20] },
    { id: 'south-west', name: '남서쪽 충전소', pos: [-25, 2.5, 20] },
    { id: 'north-east', name: '북동쪽 충전소', pos: [25, 2.5, -20], color: 0x4CAF50 },
    { id: 'south-east', name: '남동쪽 충전소', pos: [25, 2.5, 20], color: 0xF44336 },
    { id: 'center', name: '중앙 충전소', pos: [0, 2.5, 0] },
].forEach(cfg => {
    const charger = Charger.create(scene, {
        id: cfg.id,
        name: cfg.name,
        position: new THREE.Vector3(...cfg.pos),
        color: cfg.color,
    }, labelManager);
    chargerManager.registerCharger(charger);
});
chargerManager.requestCharge('robot-001');

const truckManager = new TruckManager(scene);
const inboundManager = new InboundZoneManager(scene);

// 트럭
const truck = truckManager.createTruck({
    id: 'truck-1',
    position: new THREE.Vector3(-205, 5, 80),
    boxCount: 100,
});

inboundManager.requestEntry(truck);

// // 트럭 퇴장 후 재사용
// inboundManager.onTruckExit = (truck) => {
//     truckManager.releaseTruck(truck);
// };

// 애니메이션 루프
let lastRenderTime = 0;
const targetFPS = 30;
const frameDuration = 1000 / targetFPS;

function animate(now) {
    requestAnimationFrame(animate);
    if (now - lastRenderTime < frameDuration) return;
    lastRenderTime = now;

    if (currentTargetIndex < robotPath.length) {
        const target = robotPath[currentTargetIndex];
        const dir = target.clone().sub(robot.position);
        if (dir.length() < 0.2) currentTargetIndex++;
        else robot.position.add(dir.normalize().multiplyScalar(0.1));
    }
    inboundManager.update();
    labelManager.updateAll();
    chargerManager.updateLabels(camera);
    renderer.render(scene, camera);
}
requestAnimationFrame(animate);

// 기타 이벤트
window.addEventListener('orderConfirmed', () => {
    camera.position.set(0, 150, 0);
    camera.lookAt(0, 0, 0);
    document.getElementById('kiosk')?.classList.remove('open');
});

window.addEventListener('resize', () => {
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(window.innerWidth, window.innerHeight);
});