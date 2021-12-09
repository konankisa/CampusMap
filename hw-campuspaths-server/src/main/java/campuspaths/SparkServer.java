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

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Spark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        // TODO: Create all the Spark Java routes you need here.

        // A field for the CampusMap object
        CampusMap map = new CampusMap();

        // Returns a list of the buildings in shortname: longname form in JSON
        Spark.get("/buildings", (request, response) -> {
            Gson gson = new Gson();
            Map<String, String> buildings = new HashMap<>(map.buildingNames());
            ArrayList<String[]> buildingsArr = new ArrayList<>();
            for (String building : buildings.keySet()) {
                buildingsArr.add(new String[]{building, buildings.get(building)});
            }
            return gson.toJson(buildingsArr);
        });

        // Returns a path between 2 buildings in JSON
        Spark.get("/find-path", (request, response) -> {
            Gson gson = new Gson();
            String start = request.queryParams("start");
            String end = request.queryParams("end");
            if (Objects.equals(start, "") || Objects.equals(end, "")) {
                Spark.halt(400, "Must have start and end names");
            }
            Path<Point> path = map.findShortestPath(start, end);
            return gson.toJson(path);
        });
    }
}
