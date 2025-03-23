export function createRobot(scene) {
    const robot = new THREE.Mesh(
      new THREE.SphereGeometry(2, 32, 32),
      new THREE.MeshStandardMaterial({
        color: 0x00ACC1,
        metalness: 0.7,
        roughness: 0.2
      })
    );
    robot.position.set(-145, 5, 0);
    scene.add(robot);
    return robot;
}

export const robotPath = [
    new THREE.Vector3(-20, 1, 20),   // 입구
    new THREE.Vector3(120, 1, 20),   // 입구
    new THREE.Vector3(145, 1, 0),    // 충전소

];
  