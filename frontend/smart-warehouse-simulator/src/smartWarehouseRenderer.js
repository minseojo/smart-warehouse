import * as THREE from 'three';
import { CSS2DObject } from 'three/examples/jsm/renderers/CSS2DRenderer';

// === 정적 구조 ===
export function fetchWarehouseStructure(scene) {
    return fetch('http://localhost:8080/api/warehouses/1/structure')
        .then(res => res.json())
        .then(structure => {
            drawWarehouseStructure(scene, structure);
        });
}

export function drawWarehouseStructure(scene, structure) {
    const { walls, zones } = structure;
    walls.forEach(wall => drawWall(scene, wall));
    zones.forEach(zone => drawZone(scene, zone));
}

// === 동적 상태 ===
export function fetchWarehouseState(agvGroup, rackGroup, clearGroupFn) {
    return fetch('http://localhost:8080/api/warehouses/1/state')
        .then(res => res.json())
        .then(state => {
            clearGroupFn(agvGroup);
            clearGroupFn(rackGroup);

            state.racks.forEach(rack => drawRack(rackGroup, rack));
            state.agvs.forEach(agv => drawAGV(agvGroup, agv));
        })
        .catch(error => {
            console.error("🚨 상태 가져오기 실패:", error);
        });
}

// === INIT ===
export function initializeWarehouseScene(scene) {
    const agvGroup = new THREE.Group();
    const rackGroup = new THREE.Group();
    scene.add(agvGroup);
    scene.add(rackGroup);

    // 1회 초기 호출
    fetchWarehouseStructure(scene);
    fetchWarehouseState(agvGroup, rackGroup);

    return { agvGroup, rackGroup };
}


// === AGV ===
export function drawAGV(group, agv) {
    const { position, size, rotation, name, batteryPercentage } = agv;

    const material = new THREE.MeshStandardMaterial({ color: 0x3498db });
    const geometry = new THREE.BoxGeometry(size.width, size.height, size.depth);
    const mesh = new THREE.Mesh(geometry, material);
    mesh.position.set(position.x, position.y, position.z);
    mesh.quaternion.set(rotation.x, rotation.y, rotation.z, rotation.w);

    const label = makeLabel(`${name} (${batteryPercentage.toFixed(1)}%)`, size.height / 2 + 5);
    mesh.add(label);
    group.add(mesh);
}

// === Wall ===
export function drawWall(scene, wall) {
    const { position, size, rotation, description, color } = wall;

    const material = new THREE.MeshStandardMaterial({ color });
    const geometry = new THREE.BoxGeometry(size.width, size.height, size.depth);
    const mesh = new THREE.Mesh(geometry, material);
    mesh.position.set(position.x, position.y, position.z);
    mesh.quaternion.set(rotation.x, rotation.y, rotation.z, rotation.w);
    scene.add(mesh);

    const label = makeLabel(description, size.height / 2 + 5);
    mesh.add(label);
}

// === Zone ===
export function drawZone(scene, zone) {
    const { position, size, color, name } = zone;

    const material = new THREE.MeshStandardMaterial({
        color, transparent: true, opacity: 0.4, side: THREE.DoubleSide
    });
    const geometry = new THREE.PlaneGeometry(size.width, size.height);
    const plane = new THREE.Mesh(geometry, material);
    plane.rotation.x = -Math.PI / 2;
    plane.position.set(position.x, position.y + 0.1, position.z);
    scene.add(plane);

    const label = makeLabel(name, 0);
    plane.add(label);
}

// === Rack ===
export function drawRack(group, rack) {
    const { position, size, rotation, name } = rack;

    const material = new THREE.MeshStandardMaterial({ color: 0x8B4513 });
    const geometry = new THREE.BoxGeometry(size.width, size.height, size.depth);
    const mesh = new THREE.Mesh(geometry, material);
    mesh.position.set(position.x, position.y, position.z);
    mesh.quaternion.set(rotation.x, rotation.y, rotation.z, rotation.w);

    const label = makeLabel(name, size.height / 2 + 5);
    mesh.add(label);
    group.add(mesh);
}

// === Label 생성 ===
function makeLabel(text, yOffset = 0) {
    const div = document.createElement('div');
    div.className = 'label';
    div.textContent = text;
    div.style.color = 'white';
    div.style.fontSize = '10px';
    div.style.background = 'rgba(0,0,0,0.6)';
    div.style.padding = '1px 3px';
    div.style.borderRadius = '3px';

    const label = new CSS2DObject(div);
    label.position.set(0, yOffset, 0);
    return label;
}