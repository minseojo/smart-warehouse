import * as THREE from 'three'
import { CSS2DObject } from 'three/examples/jsm/renderers/CSS2DRenderer'

export function fetchWarehouseData(scene) {
    return fetch('http://localhost:8080/api/warehouses/1')
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch warehouse data');
            return response.json();
        })
        .then(warehouse => {
            console.log('Fetched warehouse:', warehouse);
            drawWarehouse(scene, warehouse);
        })
        .catch(error => console.error('Error fetching warehouse data:', error));
}

export function drawWarehouse(scene, warehouse) {
    const { position, size, rotation, zones, walls } = warehouse;
    walls.forEach(wall => drawWall(scene, wall));
    zones.forEach(zone => drawZone(scene, zone));
}

export function drawWall(scene, wall) {
    let color = wall.color;
    const wallMaterial = new THREE.MeshStandardMaterial({ color: color });

    const { position, size, rotation, description } = wall;

    const wallMesh = new THREE.Mesh(new THREE.BoxGeometry(size.width, size.height, size.depth), wallMaterial);

    wallMesh.position.set(position.x, position.y, position.z);
    wallMesh.quaternion.set(rotation.x, rotation.y, rotation.z, rotation.w);

    scene.add(wallMesh);

    // label
    const labelDiv = document.createElement('div');
    labelDiv.className = 'label';
    labelDiv.textContent = description;
    labelDiv.style.color = 'black';

    const label = new CSS2DObject(labelDiv);
    label.position.set(position.x, position.y, position.z);
}

export function drawZone(scene, zone) {
    // ===== Zone 색상 지정 =====
    let color = zone.color;
    // ====== Material ======
    const material = new THREE.MeshStandardMaterial({
        color: color,
        transparent: true,
        opacity: 0.5,
        side: THREE.DoubleSide
    });

    const { width, height } = zone.size;
    const { x, y, z } = zone.position;

    const plane = new THREE.Mesh(new THREE.PlaneGeometry(width, height), material);

    plane.rotation.x = -Math.PI / 2;
    plane.position.set(x, y + 0.1, z); // 살짝 띄우기
    scene.add(plane);

    // ====== Label ======
    const labelDiv = document.createElement('div');
    labelDiv.className = 'label';
    labelDiv.textContent = zone.name;
    labelDiv.style.color = 'black';
    labelDiv.style.fontSize = '12px';
    labelDiv.style.background = 'rgba(255,255,255,0.7)';
    labelDiv.style.padding = '2px 4px';
    labelDiv.style.borderRadius = '4px';

    const label = new CSS2DObject(labelDiv);
    label.position.set(0, 0, 0);
    plane.add(label);
}
