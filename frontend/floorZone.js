export function createFloorZones(scene, camera) {
    const zones = [
      {
        name: 'Charging Zone',
        color: 'yellow',
        size: [15, 10],
        position: [-100, -40]
      },
      {
        name: 'Charging Zone',
        color: 'yellow',
        size: [15, 10],
        position: [-100, 40]
      },
      {
        name: 'Charging Zone',
        color: 'yellow',
        size: [15, 10],
        position: [100, -40]
      },
      {
        name: 'Charging Zone',
        color: 'yellow',
        size: [15, 10],
        position: [100, 40]
      },
      {
        name: 'Charging Zone',
        color: 'yellow',
        size: [15, 10],
        position: [0, 0]
      },
      {
        name: 'Reception Zone',
        color: 0xC8E6C9,
        size: [70, 40],
        position: [-120, -85],
        labelPosition: [-120, -65],
      },
    //   {
    //     name: 'Unloading Zone',
    //     color: 0xFFF9C4,
    //     size: [100, 40],
    //     position: [150, -80]
    //   },
      {
        name: 'Placement Zone',
        color: 0xE1BEE7,
        size: [90, 50],
        position: [-110, 75],
        labelPosition: [-110, 51],
      },
      {
        name: 'Storage Zone',
        color: 0xBFEFFF,
        size: [200, 50],
        position: [50, 75],
        labelPosition: [50, 51],
      }
    ];
  
    zones.forEach(zone => {
      const [width, depth] = zone.size;
      const [x, z] = zone.position;
  
      // 바닥 zone 생성
      const floor = new THREE.Mesh(
        new THREE.PlaneGeometry(width, depth),
        new THREE.MeshStandardMaterial({
          color: zone.color,
          transparent: true,
          opacity: 0.85
        })
      );
      floor.rotation.x = -Math.PI / 2;
      floor.position.set(x, 0.01, z);
      scene.add(floor);

      if (zone.labelPosition === undefined) return;
      const [labelX, labelY] = zone.labelPosition;
      // 라벨 생성 (한 번만 위치 계산)
      createHtmlLabel(zone.name, labelX, 5, labelY, camera);
    });
  }
  
  function createHtmlLabel(text, x, y, z, camera) {
    if (!camera) {
      console.error('camera가 undefined입니다.');
      return;
    }
  
    camera.updateMatrixWorld();
    camera.updateProjectionMatrix();
  
    const div = document.createElement('div');
    div.textContent = text;
    div.style.position = 'absolute';
    div.style.backgroundColor = '#ffffffcc';
    div.style.padding = '4px 8px';
    div.style.borderRadius = '4px';
    div.style.fontSize = '14px';
    div.style.fontWeight = 'bold';
    div.style.color = '#000';
    div.style.pointerEvents = 'none';
    div.style.zIndex = '15';
    div.style.transform = 'translate(-50%, -50%)'; // 중앙 정렬
  
    // 🎯 정확한 3D → 2D 위치 변환
    const vector = new THREE.Vector3(x, y, z).project(camera);
    const screenX = (vector.x + 1) / 2 * window.innerWidth;
    const screenY = (-vector.y + 1) / 2 * window.innerHeight;
  
    div.style.left = `${screenX}px`;
    div.style.top = `${screenY }px`;
  
    document.body.appendChild(div);
}
  