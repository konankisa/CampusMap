export class Edge {
    // coords of the first point
    p1: number[];

    // coords of the second point
    p2: number[];

    // color of the edges to be drawn
    color: string;

    constructor(p1: number[], p2: number[], color: string) {
        this.p1 = p1;
        this.p2 = p2;
        this.color = color;
    }
}