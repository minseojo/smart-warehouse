import * as THREE from 'three';

export function createFloorZones(scene, camera, labelManager) {
    const chargingPositions = [
        [-25, -20], [-25, 20], [25, -20], [25, 20], [0, 0]
    ];

    const zones = [
        ...chargingPositions.map(pos => ({
            name: 'Charging Zone',
            color: 'yellow',
            size: [15, 10],
            position: pos
        })),
        {
            name: '입고 구역',
            color: 0xC8E6C9,
            size: [110, 100],
            position: [-150, 50],
            labelPosition: [-150, 50],
        },
        {
            name: '출고 구역',
            color: 0xEFBEE7,
            size: [110, 100],
            position: [-150, -50],
            labelPosition: [-150, -50],
        },
        {
            name: '입고 보관 구역',
            // color: 0xBFEFFF,
            color: 0xC8FFFF,
            size: [150, 100],
            position: [130, 50],
            labelPosition: [130, 50],
        },
        {
            name: '출고 보관 구역',
            color: 0xFFEFFF,
            size: [150, 100],
            position: [130, -50],
            labelPosition: [130, -50],
        }
    ];

    zones.forEach(zone => {
        const name = zone.name;
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
        const [labelX, labelZ] = zone.labelPosition;

        // 왼쪽 벽 + 입출고 게이트
        createLeftWallsAndGates(scene);
        // 라벨 추가
        labelManager.addLabel({
            text: name,
            target: new THREE.Vector3(labelX, 5, labelZ)
        });
    });

    function createLeftWallsAndGates(scene) {
        const wallMaterial = new THREE.MeshPhongMaterial({ color: '#B0BEC5' });

        const wallHeight = 10;
        const wallThickness = 1;
        const wallZStart = -100;
        const wallZEnd = 100;
        const gateCount = 6;
        const totalHeight = wallZEnd - wallZStart; // 200
        const gateSection = totalHeight / gateCount; // 각 게이트 섹션: 약 33.33
        const gateHeight = 20; // 게이트 실제 높이 (z축 방향)

        for (let i = 0; i < gateCount; i++) {
            const sectionStart = wallZStart + i * gateSection;
            let sectionCenter = sectionStart + gateSection / 2;

            const isInbound = i >= 3;
            const gateColor = isInbound ? 0x00ff00 : 0xff0000;

            const gate = new THREE.Mesh(
                new THREE.BoxGeometry(wallThickness, 2, gateHeight),
                new THREE.MeshBasicMaterial({ color: gateColor })
            );
            gate.position.set(-200, 1, sectionCenter);
            scene.add(gate);

            const gateNumber = isInbound
                ? gateCount - i               // 하단 3개 (3~1번)
                : i + 1;                      // 상단 3개 (4~6번)

            const labelText = isInbound
                ? `입고 ${gateNumber}`
                : `출고 ${gateNumber}`;

            const labelOffsetZ = isInbound ? -gateHeight / 2 + 12 : gateHeight / 2 - 8;
            const labelPosition = new THREE.Vector3(-200, 5, sectionCenter + labelOffsetZ);

            labelManager.addLabel({
                text: labelText,
                target: labelPosition,
                rotate: -90
            });

            // 상단 벽
            const topWallHeight = (gateSection - gateHeight) / 2;
            if (topWallHeight > 0) {
                const topWall = new THREE.Mesh(
                    new THREE.BoxGeometry(wallThickness, wallHeight, topWallHeight),
                    wallMaterial
                );
                topWall.position.set(-200, wallHeight / 2, sectionCenter - gateHeight / 2 - topWallHeight / 2);
                scene.add(topWall);

                const bottomWall = new THREE.Mesh(
                    new THREE.BoxGeometry(wallThickness, wallHeight, topWallHeight),
                    wallMaterial
                );
                bottomWall.position.set(-200, wallHeight / 2, sectionCenter + gateHeight / 2 + topWallHeight / 2);
                scene.add(bottomWall);
            }
        }
    }
}