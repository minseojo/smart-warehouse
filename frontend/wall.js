export function createWalls(scene) {
    const wallMaterial = new THREE.MeshPhongMaterial({ color: '#B0BEC5' });

    // 상단/하단/우측 벽
    const frontWall = new THREE.Mesh(new THREE.BoxGeometry(400, 10, 1), wallMaterial);
    frontWall.position.set(0, 5, -100);
    scene.add(frontWall);

    const rightWall = new THREE.Mesh(new THREE.BoxGeometry(200, 10, 1), wallMaterial);
    rightWall.position.set(200, 5, 0);
    rightWall.rotation.y = Math.PI / 2;
    scene.add(rightWall);

    const backWall = new THREE.Mesh(new THREE.BoxGeometry(400, 10, 1), wallMaterial);
    backWall.position.set(0, 5, 100);
    scene.add(backWall);
}
