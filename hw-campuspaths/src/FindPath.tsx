import React, {Component} from "react";

interface FindPathState {
    start: string;  // the building to map the path from
    end: string;    // the building to map the path to
    path: [number, number][];    // the path between the 2 buildings
}

interface FindPathProps {
    src: string;
    dest: string;
    onPathChange(path: [number, number][], draw: boolean): void;
}

// A button that allows users to find the path between 2 buildings
class FindPath extends Component<FindPathProps, FindPathState> {
    constructor(props: any) {
        super(props);
        this.state = {
            start: this.props.src,
            end: this.props.dest,
            path: [],
        }
    }

    // Finds the path between 2 buildings
    findPath = async () => {
        try {
            let url = "http://localhost:4567/find-path?start=" + `${this.props.src}` +
                "&end=" + `${this.props.dest}`;
            let response = await fetch(url);
            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return;
            }
            let parsed = await response.json();

            if (parsed === null) {
                alert("There is no path found")
            } else {
                let path = parsed["path"];
                console.log(path)
                let temp: [number, number][] = [];
                // Makes the tuple array with the coords returned
                for (let i = 0; i < path.length; i++) {
                    let segment = path[i];
                    let end = segment["end"];
                    temp.push([end["x"], end["y"]]);
                }
                console.log(temp);
                this.setState({
                    path: temp,
                })
                console.log(this.state.path)
                this.props.onPathChange(this.state.path, true);
            }
        } catch (e) {
            alert("Error contacting the server");
            console.log(e);
        }
    }

    // Resets the canvas and the dropdowns
    reset = () => {
        this.setState({
            start: "",
            end: "",
            path: [],
        })
        this.props.onPathChange([], false);
    }

    render() {
        return (
            <div>
                <div id="buttons">
                    <button id="path" onClick={this.findPath}>Find the path!</button>
                    <button id="reset" onClick={this.reset}>Reset</button>
                </div>
            </div>
        )
    }

}

export default FindPath;