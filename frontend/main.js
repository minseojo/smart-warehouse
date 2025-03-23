import { createWalls } from './wall.js';
import { createRobot, robotPath } from './robot.js';
import { createRacks } from './rack.js';
import { Charger } from './charger.js';
import { ChargerManager } from './chargerManager.js';
import { createFloorZones } from './floorZone.js';


// 씬/카메라/렌더러 초기화
const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(
  75,
  window.innerWidth / window.innerHeight,
  0.1,
  1000
);
const renderer = new THREE.WebGLRenderer({ antialias: true });
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

// 카메라 위치 저장용
const originalCameraPosition = new THREE.Vector3();
const originalCameraTarget = new THREE.Vector3();

// 기본 조명
scene.add(new THREE.AmbientLight(0xffffff, 0.8));
const directionalLight = new THREE.DirectionalLight(0xffffff, 0.6);
directionalLight.position.set(10, 20, 10);
scene.add(directionalLight);

// 바닥
const floor = new THREE.Mesh(
    new THREE.PlaneGeometry(300, 200),
    new THREE.MeshStandardMaterial({
      color: 0xECEFF1,
      metalness: 0.1,
      roughness: 0.8
    })
);
floor.rotation.x = -Math.PI / 2;
scene.add(floor);
   
// 벽 생성
createWalls(scene);


// Rack 생성
const zoneA = createRacks(scene, {
    rows: 2,
    columns: 4,
    startX: -140,
    startZ: -90,
    rackSpacingX: 15,
    rackSpacingZ: 15,
    rackGroupId: "ZONE-A",
    category: "전자부품",
    rackWidth: 8,
    rackHeight: 12,
    rackDepth: 5
});

// Placemen
const zoneB = createRacks(scene, {
    rows: 3,
    columns: 5,
    startX: -140,
    startZ: 60,
    rackSpacingX: 15,
    rackSpacingZ: 15,
    rackGroupId: "ZONE-C",
    category: "저장소",
    rackWidth: 8,
    rackHeight: 12,
    rackDepth: 5
});

// 스토리지
const zoneC = createRacks(scene, {
    rows: 3,
    columns: 13,
    startX: -40,
    startZ: 60,
    rackSpacingX: 15,
    rackSpacingZ: 15,
    rackGroupId: "ZONE-B",
    category: "생활용품",
    rackWidth: 8,
    rackHeight: 12,
    rackDepth: 5
});

// 픽업존
const pickupZoneA = new THREE.Mesh(
    new THREE.BoxGeometry(10, 1, 30),
    new THREE.MeshPhongMaterial({ color: 0xFFCA28 }) // 옐로우
  );
pickupZoneA.position.set(-140, 4, 0);

const pickupZoneB = new THREE.Mesh(
    new THREE.BoxGeometry(10, 1, 30),
    new THREE.MeshPhongMaterial({ color: 0xFFCA28 }) // 옐로우
  );
pickupZoneB.position.set(140, 4, 0);

scene.add(pickupZoneA);
scene.add(pickupZoneB);
  
// 충전소
const chargerManager = new ChargerManager();

const chargerNorthWest = Charger.create(scene, {
    id: 'north-west',
    name: '북서쪽 충전소',
    position: new THREE.Vector3(-100, 2.5, -40)
});
  
const chargerSouthWest = Charger.create(scene, {
    id: 'south-west',
    name: '남서쪽 충전소',
    position: new THREE.Vector3(-100, 2.5, 40)
});
  
const chargerNorthEast = Charger.create(scene, {
    id: 'north-east',
    name: '북동쪽 충전소',
    position: new THREE.Vector3(100, 2.5, -40),
    color: 0x4CAF50 // green
});
  
const chargerSouthEast = Charger.create(scene, {
    id: 'south-east',
    name: '남동쪽 충전소',
    position: new THREE.Vector3(100, 2.5, 40),
    color: 0xF44336 // red
});

const chargerCenter = Charger.create(scene, {
    id: 'center',
    name: '중앙 충전소',
    position: new THREE.Vector3(0, 2.5, 0),
});



[chargerNorthWest, chargerSouthWest, chargerNorthEast, chargerSouthEast, chargerCenter].forEach(charger => {
  chargerManager.registerCharger(charger);
});

// 로봇 충전 요청 예시
chargerManager.requestCharge('robot-001');

// 로봇 생성 및 경로 설정
const robot = createRobot(scene);
let currentTargetIndex = 0;

// 카메라 초기 위치 설정
camera.position.set(0, 150, 0);
camera.lookAt(0, 0, 0);
originalCameraPosition.copy(camera.position);
originalCameraTarget.set(0, 0, 0);

// *카메라 위치 변경 후, 정적 존 생성 (투영 시키기 위해, 카메라 위치 변경 이후)
createFloorZones(scene, camera);

// 애니메이션 루프
let lastRenderTime = 0;
const targetFPS = 30;
const frameDuration = 1000 / targetFPS;

function animate(now) {
  requestAnimationFrame(animate);

  if (now - lastRenderTime < frameDuration) return; // 프레임 건너뛰기
  lastRenderTime = now;

  // 로봇 이동
  if (currentTargetIndex < robotPath.length) {
    const target = robotPath[currentTargetIndex];
    const direction = target.clone().sub(robot.position);
    const distance = direction.length();

    if (distance < 0.2) {
      currentTargetIndex++;
    } else {
      direction.normalize();
      robot.position.add(direction.multiplyScalar(0.1));
    }
  }

  chargerManager.updateLabels(camera);
  renderer.render(scene, camera);
}
requestAnimationFrame(animate);


// 주문 완료 시 이벤트 처리 (kiosk-ui.js → window.dispatchEvent(new Event('orderConfirmed')))
window.addEventListener('orderConfirmed', () => {
  camera.position.copy(originalCameraPosition);
  camera.lookAt(originalCameraTarget);
  const kiosk = document.getElementById('kiosk');
  kiosk?.classList.remove('open');
});

// 창 크기 변경 대응
window.addEventListener('resize', () => {
  camera.aspect = window.innerWidth / window.innerHeight;
  camera.updateProjectionMatrix();
  renderer.setSize(window.innerWidth, window.innerHeight);
});
