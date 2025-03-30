import * as THREE from 'three';
import { CSS2DRenderer } from 'three/examples/jsm/renderers/CSS2DRenderer'

import { fetchWarehouseData } from './warehouse/warehouse.js';

// 3D 씬 설정
const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
const renderer = new THREE.WebGLRenderer({ antialias: true });
const labelRenderer = new CSS2DRenderer();

// renderer
renderer.setSize(window.innerWidth, window.innerHeight);
renderer.domElement.style.display = 'block';
renderer.domElement.style.position = 'absolute';
renderer.domElement.style.top = '0';
renderer.domElement.style.left = '0';
renderer.domElement.style.width = '100%';
renderer.domElement.style.height = '100%';
document.body.appendChild(renderer.domElement);

// CSS2DRenderer
labelRenderer.setSize(window.innerWidth, window.innerHeight);
labelRenderer.domElement.style.position = 'absolute';
labelRenderer.domElement.style.top = '0';
labelRenderer.domElement.style.left = '0';
labelRenderer.domElement.style.width = '100%';
labelRenderer.domElement.style.height = '100%';
labelRenderer.domElement.style.margin = '0';
labelRenderer.domElement.style.padding = '0';
labelRenderer.domElement.style.pointerEvents = 'none';
labelRenderer.domElement.style.display = 'block';
document.body.appendChild(labelRenderer.domElement);

// 카메라 설정
camera.position.set(0, 560, 0);
camera.lookAt(0, 0, 0);

// 조명 설정
scene.add(new THREE.AmbientLight(0xffffff, 0.8));
const light = new THREE.DirectionalLight(0xffffff, 0.8);
light.position.set(10, 20, 10);
scene.add(light);

// OrbitControls로 씬 조작
// const controls = new OrbitControls(camera, renderer.domElement);
// controls.enableDamping = true;
// controls.dampingFactor = 0.05;
// controls.enableZoom = false;     // 줌 막기
// controls.enablePan = false;      // 패닝 막기
// controls.enableRotate = false;   // 회전 막기
//
// controls.update();


// 애니메이션 루프
function animate() {
    requestAnimationFrame(animate);
    // controls.update();
    renderer.render(scene, camera);
    labelRenderer.render(scene, camera);
}

animate();

// 페이지 로드 후 데이터를 불러옵니다.
window.addEventListener('DOMContentLoaded', () => {
    fetchWarehouseData(scene);
    animate();
});
