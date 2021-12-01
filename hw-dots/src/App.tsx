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
import EdgeList from "./EdgeList";
import Grid from "./Grid";
import GridSizePicker from "./GridSizePicker";

// Allows us to write CSS styles inside App.css, any any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
    gridSize: number;  // size of the grid to display
    edges: string;     // string of edges to draw
}

class App extends Component<{}, AppState> { // <- {} means no props.

    constructor(props: any) {
        super(props);
        this.state = {
            gridSize: 4,
            edges: "",
        };
    }

    updateGridSize = (newSize: number) => {
        this.setState({
            gridSize: newSize
        });
    };

    // Function to send an empty string or a string of edges
    // based on the button being clicked
    edgeInput = (cur: string, clear: boolean) => {
        if (clear) {
            this.setState({
                edges: "",
            })
        } else {
            this.setState({
                edges: cur,
            })
        }
    }

    render() {
        let val: number = 0;
        let str_displayed = this.state.gridSize.toString();
        const canvas_size = 500;
        if (str_displayed != "NaN") {
            val = this.state.gridSize;
        }
        return (
            <div>
                <p id="app-title">Connect the Dots!</p>
                <GridSizePicker value={str_displayed} onChange={this.updateGridSize}/>
                <Grid size={val} width={canvas_size} height={canvas_size} edges={this.state.edges}/>
                <EdgeList onChange={this.edgeInput}/>
            </div>
        );
    }

}

export default App;
