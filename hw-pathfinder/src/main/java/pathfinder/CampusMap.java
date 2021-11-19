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

package pathfinder;

import graph.LabeledDGraph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pathfinder.parser.CampusPathsParser.parseCampusBuildings;

/**
 * This class implements the ModelAPI interface in order to represent a map of a campus.
 *
 * @spec.specfield paths: LabeledDGraph<Point, Double> // stores the paths between all points in the campus
 * @spec.specfield buildings: HashMap<String, CampusBuilding> // stores all the buildings in the campus
 */
@SuppressWarnings("ALL")
public class CampusMap implements ModelAPI {

    // Representation Invariant:
    // paths != null. buildings != null. Point values != null.
    // Buildings and their short names cannot be null

    // Abstraction Function:
    // A CampusMap c represents a map that contains buildings and the paths connecting said buildings
    // This is such that all the paths in c are represented by the directed graph "paths",
    // and all the buildings in c are represented by the map buildings.

    /**
     * Stores the paths between all given points in the campus
     */
    private final LabeledDGraph<Point, Double> paths;

    /**
     * Stores all the buildings in the campus
     */
    private final HashMap<String, CampusBuilding> buildings;

    /**
     * Toggles the expensive checkRep() computations
     */
    private final boolean DEBUG = false;

    /**
     * Constructs a new CampusMap using the given files for buildings and paths
     *
     * @spec.effects constructs a CampusMap
     */
    public CampusMap() {
        this.buildings = new HashMap<>();
        this.paths = new LabeledDGraph<>();
        List<CampusBuilding> buildList = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        for (CampusBuilding campusBuilding : buildList) {
            buildings.put(campusBuilding.getShortName(), campusBuilding);
        }
        List<CampusPath> pathList = CampusPathsParser.parseCampusPaths("campus_paths.csv");
        for (CampusPath campusPath : pathList) {
            paths.addEdge(new Point(campusPath.getX1(), campusPath.getY1()), campusPath.getDistance(),
                    new Point(campusPath.getX2(), campusPath.getY2()));
        }
        checkRep();
    }

    /**
     * Throws an exception if the representation invariant is violated
     */
    public void checkRep() {
        assert (paths != null) : "paths == null";
        assert (buildings != null) : "buildings == null";
        if (DEBUG) {
            for (String name : buildings.keySet()) {
                assert (name != null) : "Short name of the building is null";
                assert (buildings.get(name) != null) : "Building is null";
            }
            for (Point point : paths.getNodes()) {
                assert (point != null) : "Point == null";
            }
        }
    }

    @Override
    public boolean shortNameExists(String shortName) {
        checkRep();
        List<CampusBuilding> list = parseCampusBuildings("campus_buildings.csv");
        for (CampusBuilding building : list) {
            if (building.getShortName().equals(shortName)) {
                return true;
            }
        }
        checkRep();
        return false;
    }

    @Override
    public String longNameForShort(String shortName) {
        checkRep();
        if (!shortNameExists(shortName)) {
            throw new IllegalArgumentException("The given short name does not exist");
        }
        List<CampusBuilding> list = parseCampusBuildings("campus_buildings.csv");
        for (CampusBuilding building : list) {
            if (building.getShortName().equals(shortName)) {
                return building.getLongName();
            }
        }
        checkRep();
        return null;
    }

    @Override
    public Map<String, String> buildingNames() {
        checkRep();
        List<CampusBuilding> list = parseCampusBuildings("campus_buildings.csv");
        Map<String, String> finalMap = new HashMap<>();
        for (CampusBuilding building : list) {
            finalMap.put(building.getShortName(), building.getLongName());
        }
        checkRep();
        return finalMap;
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        checkRep();
        if (startShortName == null || endShortName == null) {
            throw new IllegalArgumentException("Building names cannot be null");
        }
        if (!shortNameExists(startShortName)) {
            throw new IllegalArgumentException("The given start short name does not exist");
        }
        if (!shortNameExists(endShortName)) {
            throw new IllegalArgumentException("The given end short name does not exist");
        }
        Point startPoint = null;
        Point endPoint = null;
        for (String shortName : buildings.keySet()) {
            if (shortName.equals(startShortName)) {
                startPoint = new Point(buildings.get(shortName).getX(), buildings.get(shortName).getY());
            }
            if (shortName.equals(endShortName)) {
                endPoint = new Point(buildings.get(shortName).getX(), buildings.get(shortName).getY());
            }
        }
        Path<Point> finalPath = DijkstraAlg.dijsktra(startPoint, endPoint, paths);
        checkRep();
        return finalPath;
    }

}
