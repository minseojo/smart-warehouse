import * as THREE from 'three';

export class LabelManager {
    constructor(camera) {
        this.camera = camera;
        this.labels = [];
    }

    /**
     * 라벨 추가
     * @param {Object} options
     * @param {string} options.text
     * @param {THREE.Object3D | THREE.Vector3} options.target - 3D 위치 대상
     * @param {function=} options.getText - 매 프레임마다 텍스트 업데이트할 함수
     * @returns {HTMLElement}
     */
    addLabel({ text, target, getText = null, rotate = 0 }) {
        const div = document.createElement('div');
        div.textContent = text;
        div.style.position = 'absolute';
        div.style.backgroundColor = '#ffffffcc';
        div.style.padding = '4px 8px';
        div.style.borderRadius = '4px';
        div.style.fontSize = '12px';
        div.style.fontWeight = 'bold';
        div.style.color = '#000';
        div.style.pointerEvents = 'none';
        div.style.zIndex = '15';

        // 회전 적용
        div.style.transform = `translate(-50%, -50%) rotate(${rotate}deg)`;

        document.body.appendChild(div);
        this.labels.push({ element: div, target, getText });
        return div;
    }

    updateAll() {
        const width = window.innerWidth / 2;
        const height = window.innerHeight / 2;

        this.labels.forEach(({ element, target, getText }) => {
            if (!target) return;

            let pos = target instanceof THREE.Object3D ? target.position.clone() : target.clone();
            const vector = pos.project(this.camera);

            // 화면 밖이면 숨김 처리
            if (vector.z < -1 || vector.z > 1) {
                element.style.display = 'none';
                return;
            }

            element.style.display = 'block';
            element.style.left = `${vector.x * width + width}px`;
            element.style.top = `${-vector.y * height + height - 10}px`;

            if (getText) {
                element.textContent = getText();
            }
        });
    }

    clear() {
        this.labels.forEach(({ element }) => {
            if (element && element.parentNode) {
                element.parentNode.removeChild(element);
            }
        });
        this.labels = [];
    }
}
