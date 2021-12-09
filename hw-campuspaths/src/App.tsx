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
import MapView from "./MapView";
import BuildingPickers from "./BuildingPickers";
import "./App.css";
import FindPath from "./FindPath";

interface AppState {
    start: string;  // the building to map the path from
    end: string;    // the building to map the path to
    path: [number, number][]     // the path between the 2 buildings
    draw: boolean   // boolean to draw or clear path and buildings
}

class App extends Component<{}, AppState> {

    constructor(props: any) {
        super(props);
        this.state = {
            start: "",
            end: "",
            path: [],
            draw: true,
        }
    }

    // Sets the building to map the path from
    setSrc = (src: string) => {
        this.setState({
            start: src,
        })
    }

    // Sets the building to map the path to
    setDest = (dest: string) => {
        this.setState({
            end: dest,
        })
    }

    // Gets the path of the buildings
    getPath = (path: any[], draw: boolean) => {
        if ((this.state.start === "" || this.state.end === "") || !draw) {
            this.setState({
                start: "",
                end: "",
                path: [],
            })
        } else {
            this.setState({
                path: path,
            })
        }
    }

    render() {
        return (
            <div>
                <p id="map-view">UW Campus Map:</p>
                <BuildingPickers onStartChange={this.setSrc} onEndChange={this.setDest} draw={this.state.draw}
                                            src={this.state.start} dest={this.state.end}/>
                <FindPath src={this.state.start} dest={this.state.end} onPathChange={this.getPath}/>
                <MapView path={this.state.path} start={this.state.path[0]}
                         end={this.state.path[this.state.path.length - 1]}/>
            </div>
        );
    }

}

export default App;
