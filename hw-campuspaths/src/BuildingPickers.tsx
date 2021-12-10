import React, {Component} from "react";

interface BuildingProps {
    onStartChange(start: string): void;
    onEndChange(end: string): void;
    draw: boolean;
    src: string;
    dest: string;
}

interface BuildingState {
    start: string;
    end: string;
    buildings: any[];
}

class BuildingPickers extends Component<BuildingProps, BuildingState> {

    constructor(props: BuildingProps) {
        super(props);
        this.state = {
            start: "",
            end: "",
            buildings: [],
        }
    }

    componentDidMount() {
        this.getBuildings().then(() => console.log());
    }

    componentDidUpdate() {
        this.getBuildings().then(() => console.log());
    }

    // Gets the list of buildings
    getBuildings = async () => {
        try {
            let response = await fetch("http://localhost:4567/buildings")
            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return;
            }
            let buildingList = (await response.json()) as string[];

            let names = [];
            for (let building of buildingList) {
                names.push({short: building[0].toString(), long: building[1].toString()});
            }
            this.setState({
                buildings: [{short: "", long: "Choose one"}].concat(names),
            });

        } catch (e) {
            alert("Error contacting the server");
            console.log(e);
        }
    }

    onStartChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        let start = event.target.value;
        this.setState({
            start: start,
        })
        if (start !== "" || start !== null) {
            this.props.onStartChange(start);
        }
        if (!this.props.draw) {
            this.setState({
                buildings: ["", "Choose one"],
            })
        }
    }

    onEndChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        let end = event.target.value;
        this.setState({
            end: end,
        })
        if (end !== "" || end !== null) {
            this.props.onEndChange(end);
        }
        if (!this.props.draw) {
            this.setState({
                buildings: ["", "Choose one"],
            })
        }
    }

    render() {
        return (
            <div id="inputs">
                <label id="src" htmlFor="Buildings">Choose your start: </label>
                <select value={this.props.src} onChange={this.onStartChange} name={"Start"} id={"Start"}>
                    {this.state.buildings.map((building) => <option key={building.short}
                        value={building.short}>{building.long}</option>)}
                </select>
                <p>to</p>
                <label id="dest" htmlFor="Buildings">Choose your destination: </label>
                <select value={this.props.dest} onChange={this.onEndChange} name={"End"} id={"End"}>
                    {this.state.buildings.map((building) => <option key={building.short}
                        value={building.short}>{building.long}</option>)}
                </select>
            </div>
        );
    }
}

export default BuildingPickers;