/*
 * Copyright (C) 2021 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';
import {Edge} from "./Edge";
import {isString} from "util";

interface GridProps {
    size: number;    // size of the grid to display
    width: number;   // width of the canvas on which to draw
    height: number;  // height of the canvas on which to draw
    edges: string;   // edges that need to be drawn
}

interface GridState {
    backgroundImage: any,  // image object rendered into the canvas (once loaded)
}

/**
 *  A simple grid with a variable size
 *
 *  Most of the assignment involves changes to this class
 */
class Grid extends Component<GridProps, GridState> {

    canvasReference: React.RefObject<HTMLCanvasElement>

    constructor(props: GridProps) {
        super(props);
        this.state = {
            backgroundImage: null  // An image object to render into the canvas.
        };
        this.canvasReference = React.createRef();
    }

    componentDidMount() {
        // Since we're saving the image in the state and re-using it any time we
        // redraw the canvas, we only need to load it once, when our component first mounts.
        this.fetchAndSaveImage();
        this.redraw();
    }

    componentDidUpdate() {
        this.redraw()
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        const background = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./image.jpg";
    }

    redraw = () => {
        if (this.canvasReference.current === null) {
            throw new Error("Unable to access canvas.");
        }
        const ctx = this.canvasReference.current.getContext('2d');
        if (ctx === null) {
            throw new Error("Unable to create canvas drawing context.");
        }

        // First, let's clear the existing drawing so we can start fresh:
        ctx.clearRect(0, 0, this.props.width, this.props.height);

        // Once the image is done loading, it'll be saved inside our state, and we can draw it.
        // Otherwise, we can't draw the image, so skip it.
        if (this.state.backgroundImage !== null) {
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }

        // Draw all the dots.
        const coordinates = this.getCoordinates();
        for (let coordinate of coordinates) {
            this.drawCircle(ctx, coordinate);
        }

        // Parse the input string of edges
        let str_arr: string[] = this.props.edges.split(/\n/);
        let edge_arr: Edge[] = [];
        let counter: number = 0;
        let in_grid: boolean = true;
        let coord_arr: number[] = [];
        let extra_space: boolean = false;
        for (let str of str_arr) {
            counter++;
            if (str === "") {
                continue;
            }
            let val: string[] = str.split(" ");

            let s1: string[] = val[0].split(",");
            let s2: string[] = val[1].split(",");
            let n1: number[] = [];
            let n2: number[] = [];
            n1.push(parseInt(s1[0]), parseInt(s1[1]));
            n2.push(parseInt(s2[0]), parseInt(s2[1]));
            coord_arr.push(n1[0], n1[1], n2[0], n2[1]);
            // Throws an alert if the number formatting is not right
            if (isNaN(n1[0]) || isNaN(n1[1]) || isNaN(n2[0])
                    || isNaN(n2[1])) {
                alert("Please enter data in the format x1,y1 x2,y2 color\nLine " + counter +
                    " is missing a space or another portion of the line");
                break;
            }
            // Throws an alert if there is an extra space in the given input anywhere
            for (let str of val) {
                if (str === "") {
                    alert("Please enter data in the format x1,y1 x2,y2 color\nLine " + counter +
                        " has an extra space or another portion of the line");
                    extra_space = true;
                    break;
                }
            }
            // Doesn't draw if there is an extra space
            if (extra_space) {
                in_grid = false;
                break;
            }
            let cur = new Edge(n1, n2, val[2]);
            edge_arr.push(cur);
        }
        // Throws an alert if the grid size is too small for the given inputs to be drawn
        if (Math.max(...coord_arr) >= this.props.size) {
            alert("Cannot draw edges, grid must be at least size " + (Math.max(...coord_arr) + 1));
            return;
        }

        // Draw the edges between points
        let scale = 500 / (this.props.size + 1);
        if (in_grid) {
            for (let edge of edge_arr) {
                ctx.beginPath()
                ctx.lineWidth = Math.min(5, 200 / (this.props.size));
                ctx.strokeStyle = edge.color;
                ctx.moveTo((edge.p1[0] + 1) * scale, (edge.p1[1] + 1) * scale);
                ctx.lineTo((edge.p2[0] + 1) * scale, (edge.p2[1] + 1) * scale);
                ctx.stroke();
            }
        }
    };

    /**
     * Returns an array of coordinate pairs that represent all the points where grid dots should
     * be drawn.
     */
    getCoordinates = (): [number, number][] => {
        // An x by x grid that scales based on the size given
        let vals: [number, number][];
        vals = [];
        for (let i = 0; i < this.props.size; i++) {
            for (let j = 0; j < this.props.size; j++) {
                vals.push([(i+1) * (500 / (this.props.size + 1)), (j+1) * 500 / (this.props.size + 1)]);
            }
        }
        return vals;
    };

    drawCircle = (ctx: CanvasRenderingContext2D, coordinate: [number, number]) => {
        ctx.fillStyle = "white";
        // Generally use a radius of 4, but when there are lots of dots on the grid (> 50)
        // we slowly scale the radius down so they'll all fit next to each other.
        const radius = Math.min(4, 100 / this.props.size);
        ctx.beginPath();
        ctx.arc(coordinate[0], coordinate[1], radius, 0, 2 * Math.PI);
        ctx.fill();
    };

    render() {
        return (
            <div id="grid">
                <canvas ref={this.canvasReference} width={this.props.width} height={this.props.height}/>
                <p>Current Grid Size: {this.props.size}</p>
            </div>
        );
    }
}

export default Grid;
