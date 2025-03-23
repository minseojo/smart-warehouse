export function createWalls(scene) {
    const wallMaterial = new THREE.MeshPhongMaterial({ color: '#B0BEC5' });
  
    const frontWall = new THREE.Mesh(new THREE.BoxGeometry(300, 10, 1), wallMaterial);
    frontWall.position.set(0, 5, -100);
    scene.add(frontWall);
  
    const rightWall = new THREE.Mesh(new THREE.BoxGeometry(200, 10, 1), wallMaterial);
    rightWall.position.set(150, 5, 0);
    rightWall.rotation.y = Math.PI / 2;
    scene.add(rightWall);
  
    const leftWall = new THREE.Mesh(new THREE.BoxGeometry(200, 10, 1), wallMaterial);
    leftWall.position.set(-150, 5, 0)
    leftWall.rotation.y = Math.PI / 2;
    scene.add(leftWall);
  
    const backWall = new THREE.Mesh(new THREE.BoxGeometry(300, 10, 1), wallMaterial);
    backWall.position.set(0, 5,100);
    scene.add(backWall);
  
    
}