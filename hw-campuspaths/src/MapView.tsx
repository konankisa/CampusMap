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
import "./MapView.css";

interface MapViewProps {
    path: [number, number][]
    start: [number, number]
    end: [number, number]
}

interface MapViewState {
    backgroundImage: HTMLImageElement | null;
}

class MapView extends Component<MapViewProps, MapViewState> {

    // NOTE:
    // This component is a suggestion for you to use, if you would like to.
    // It has some skeleton code that helps set up some of the more difficult parts
    // of getting <canvas> elements to display nicely with large images.
    //
    // If you don't want to use this component, you're free to delete it.

    canvas: React.RefObject<HTMLCanvasElement>;

    constructor(props: MapViewProps) {
        super(props);
        this.state = {
            backgroundImage: null
        };
        this.canvas = React.createRef();
    }

    componentDidMount() {
        this.fetchAndSaveImage();
        this.drawBackgroundImage();
    }

    componentDidUpdate() {
        this.drawBackgroundImage();
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        let background: HTMLImageElement = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./campus_map.jpg";
    }

    drawBackgroundImage() {
        let canvas = this.canvas.current;
        if (canvas === null) throw Error("Unable to draw, no canvas ref.");
        let ctx = canvas.getContext("2d");
        if (ctx === null) throw Error("Unable to draw, no valid graphics context.");
        //
        if (this.state.backgroundImage !== null) { // This means the image has been loaded.
            // Sets the internal "drawing space" of the canvas to have the correct size.
            // This helps the canvas not be blurry.
            canvas.width = this.state.backgroundImage.width;
            canvas.height = this.state.backgroundImage.height;
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }
        const buildings = this.markBuildings();
        let path = this.props.path
        // Marks the path according to the given coords
        for (let i = 0; i < path.length - 1; i++) {
            ctx.beginPath();
            ctx.lineWidth = 15;
            ctx.strokeStyle = "blue";
            ctx.moveTo(path[i][0], path[i][1]);
            ctx.lineTo(path[i + 1][0], path[i + 1][1]);
            ctx.stroke();
        }
        for (let building of buildings) {
            this.buildingHighlight(ctx, building);
        }
    }

    // Returns the buildings chosen by the user
    markBuildings = (): [number, number][] => {
        let startEnd: [number, number][] = [];
        if (this.props.path.length > 1) {
            startEnd.push(this.props.path[0]);
            startEnd.push(this.props.path[this.props.path.length - 1]);
        }
        return startEnd;
    }

    // Highlights the buildings chosen by the user on the campus map
    buildingHighlight = (ctx: CanvasRenderingContext2D, coordinate: [number, number]) => {
        ctx.fillStyle = "red";
        const radius = 25;
        ctx.beginPath();
        ctx.arc(coordinate[0], coordinate[1], radius, 0, 2 * Math.PI);
        ctx.fill();
    };

    render() {
        return (
            <canvas ref={this.canvas}/>
        )
    }
}

export default MapView;
