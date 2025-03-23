// rack.js

export function createRacks(scene, {
    rows = 1,
    columns = 1,
    category = "default",
    startX = 0,
    startZ = 0,
    rackSpacingX = 30,
    rackSpacingZ = 20,
    rackGroupId = "GROUP",
    rackWidth = 8,
    rackHeight = 12,
    rackDepth = 4
  } = {}) {
    const material = new THREE.MeshStandardMaterial({
      color: 0x90A4AE,
      metalness: 0.3,
      roughness: 0.4
    });
  
    const rackData = [];
  
    for (let row = 0; row < rows; row++) {
      for (let col = 0; col < columns; col++) {
        const x = startX + col * rackSpacingX;
        const z = startZ + row * rackSpacingZ;
  
        const rack = new THREE.Mesh(
          new THREE.BoxGeometry(rackWidth, rackHeight, rackDepth),
          material
        );
        rack.position.set(x, rackHeight / 2, z);
  
        const rackId = `${rackGroupId}-${row}-${col}`;
        rack.userData = { rackId, category };
  
        scene.add(rack);
  
        rackData.push({
          id: rackId,
          x,
          z,
          category,
          width: rackWidth,
          height: rackHeight,
          depth: rackDepth,
          object: rack
        });
      }
    }
  
    return rackData;
}
  