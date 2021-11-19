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

package pathfinder.scriptTestRunner;

import graph.LabeledDGraph;
import pathfinder.datastructures.Path;

import java.io.*;
import java.util.*;

import static pathfinder.DijkstraAlg.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {

    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    private final Map<String, LabeledDGraph<String, Double>> graphs = new HashMap<String, LabeledDGraph<String, Double>>();
    private final PrintWriter output;
    private final BufferedReader input;

    // Leave this constructor public
    public PathfinderTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        LabeledDGraph<String, Double> graph = new LabeledDGraph<>();

        graphs.put(graphName, graph);
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        LabeledDGraph<String, Double> graph = graphs.get(graphName);
        graph.addNode(nodeName);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        Double edgeLabel = Double.parseDouble(arguments.get(3));

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         Double edgeLabel) {
        LabeledDGraph<String, Double> graph = graphs.get(graphName);
        graph.addEdge(parentName, edgeLabel, childName);
        output.println("added edge " + String.format("%.3f", edgeLabel) + " from " +
                parentName + " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        LabeledDGraph<String, Double> graph = graphs.get(graphName);
        String res = graphName + " contains:";
        List<String> sortedList = new ArrayList<>(graph.getNodes());
        Collections.sort(sortedList);
        for (String node : sortedList) {
            res += " " + node;
        }
        output.println(res);
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        LabeledDGraph<String, Double> graph = graphs.get(graphName);
        HashSet<LabeledDGraph.Edge<String, Double>> children = graph.getChildren(parentName);
        String res = "the children of " + parentName + " in " + graphName + " are:";
        TreeSet<LabeledDGraph.Edge<String, Double>> sortedChildren = new TreeSet<>((o1, o2) -> {
            if (!o1.getChild().equals(o2.getChild())) {
                return o1.getChild().compareTo(o2.getChild());
            }
            if (!o1.getLabel().equals(o2.getLabel())) {
                return o1.getLabel().compareTo(o2.getLabel());
            }
            return 0;
        });
        sortedChildren.addAll(children);
        for (LabeledDGraph.Edge<String, Double> edge : sortedChildren) {
            res += " " + edge.getChild() + "(" + String.format("%.3f", edge.getLabel()) + ")";
        }
        output.println(res);
    }

    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to FindPath: " + arguments);
        }
        String graphName = arguments.get(0);
        String src = arguments.get(1);
        String dest = arguments.get(2);
        findPath(graphName, src, dest);
    }

    private void findPath(String graphName, String src, String dest) {
        LabeledDGraph<String, Double> graph = graphs.get(graphName);
        String res = "";
        Path<String> dijkstraList = dijkstra(src, dest, graph);
        if (!graph.containsNode(src) && !graph.containsNode(dest)) {
            res += "unknown: " + src;
            res += "\nunknown: " + dest;
        } else if (!graph.containsNode(src)) {
            res += "unknown: " + src;
        } else if (!graph.containsNode(dest)) {
            res += "unknown: " + dest;
        } else {
            res = "path from " + src + " to " + dest + ":";
            if (dijkstraList != null) {
                double totalCost = 0.000;
                for (Path<String>.Segment edge : dijkstraList) {
                    if (!src.equals(edge.getEnd())) {
                        res += "\n" + src + " to " + edge.getEnd() + " with weight " +
                                String.format("%.3f", edge.getCost());
                        totalCost += edge.getCost();
                        src = edge.getEnd();
                    }
                }
                res += "\ntotal cost: " + String.format("%.3f", totalCost);
            } else {
                res += "\nno path found";
            }
        }
        output.println(res);
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
