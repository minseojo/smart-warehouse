// types.ts (Interface 정의)
export interface RobotData {
    id: number;
    state: 'Idle' | 'Moving' | 'Charging' | 'Waiting';
    position: { x: number; y: number; };
    speed: number;
}

export interface SimulationData {
    robots: RobotData[];
    queue: number[]; // robot id array
}
