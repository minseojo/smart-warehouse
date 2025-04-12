import * as THREE from 'three';
import { CSS2DRenderer } from 'three/examples/jsm/renderers/CSS2DRenderer';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls';
import { initializeWarehouseScene, fetchWarehouseState } from './smartWarehouseRenderer.js';

// === SCENE ===
const scene = new THREE.Scene();

// === CAMERA ===
const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 1, 10000);
camera.position.set(0, 560, 0);
camera.lookAt(0, 0, 0);

// === RENDERERS ===
const renderer = new THREE.WebGLRenderer({ antialias: true });
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

const labelRenderer = new CSS2DRenderer();
labelRenderer.setSize(window.innerWidth, window.innerHeight);
labelRenderer.domElement.style.position = 'absolute';
labelRenderer.domElement.style.top = '0px';
labelRenderer.domElement.style.left = '0px';
labelRenderer.domElement.style.pointerEvents = 'none';
document.body.appendChild(labelRenderer.domElement);

// === CONTROLS ===
const controls = new OrbitControls(camera, renderer.domElement);
controls.target.set(0, 10, 0);
controls.update();

// === LIGHT ===
scene.add(new THREE.AmbientLight(0xffffff, 0.9));
const directionalLight = new THREE.DirectionalLight(0xffffff, 0.6);
directionalLight.position.set(10, 20, 10);
scene.add(directionalLight);

// === 라벨 포함 그룹 안전 제거 함수 ===
function clearGroupWithCSSLabels(group) {
    group.children.forEach(child => {
        child.children.forEach(grandChild => {
            if (grandChild.element?.parentNode) {
                grandChild.element.parentNode.removeChild(grandChild.element);
            }
        });
    });
    group.clear();
}

// === AGV/Rack Group 초기화 및 갱신 루프 ===
const { agvGroup, rackGroup } = initializeWarehouseScene(scene);

setInterval(() => {
    fetchWarehouseState(agvGroup, rackGroup, clearGroupWithCSSLabels);
}, 1000);

// === RENDER LOOP ===
function animate() {
    requestAnimationFrame(animate);
    renderer.render(scene, camera);
    labelRenderer.render(scene, camera);
}
animate();
