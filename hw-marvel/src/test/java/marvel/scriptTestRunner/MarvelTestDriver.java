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

package marvel.scriptTestRunner;

import graph.LabeledDGraph;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import static marvel.MarvelPaths.BFS;
import static marvel.MarvelPaths.pathsGraph;

/**
 * This class implements a testing driver which reads test scripts from
 * files for testing Graph, the Marvel parser, and your BFS algorithm.
 */
public class MarvelTestDriver {

    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    private final Map<String, LabeledDGraph<String, String>> graphs = new HashMap<String, LabeledDGraph<String, String>>();
    private final PrintWriter output;
    private final BufferedReader input;

    // Leave this constructor public
    public MarvelTestDriver(Reader r, Writer w) {
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
                case "LoadGraph":
                    loadGraph(arguments);
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
        LabeledDGraph<String, String> graph = new LabeledDGraph<>();

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
        LabeledDGraph<String, String> graph = graphs.get(graphName);
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
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         String edgeLabel) {
        LabeledDGraph<String, String> graph = graphs.get(graphName);
        graph.addEdge(parentName, edgeLabel, childName);
        output.println("added edge " + edgeLabel + " from " +
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
        LabeledDGraph<String, String> graph = graphs.get(graphName);
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
        LabeledDGraph<String, String> graph = graphs.get(graphName);
        HashSet<LabeledDGraph.Edge<String, String>> children = graph.getChildren(parentName);
        String res = "the children of " + parentName + " in " + graphName + " are:";
        TreeSet<LabeledDGraph.Edge<String, String>> sortedChildren = new TreeSet<>((o1, o2) -> {
            if (!o1.getChild().equals(o2.getChild())) {
                return o1.getChild().compareTo(o2.getChild());
            }
            if (!o1.getLabel().equals(o2.getLabel())) {
                return o1.getLabel().compareTo(o2.getLabel());
            }
            return 0;
        });
        sortedChildren.addAll(children);
        for (LabeledDGraph.Edge<String, String> edge : sortedChildren) {
            res += " " + edge.getChild() + "(" + edge.getLabel() + ")";
        }
        output.println(res);
    }

    private void loadGraph(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to LoadGraph: " + arguments);
        }
        String graphName = arguments.get(0);
        String fileName = arguments.get(1);
        loadGraph(graphName, fileName);
    }

    private void loadGraph(String graphName, String fileName) {
        graphs.put(graphName, pathsGraph(fileName));
        output.println("loaded graph " + graphName);
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
        LabeledDGraph<String, String> graph = graphs.get(graphName);
        String res = "";
        ArrayList<LabeledDGraph.Edge<String, String>> bfsList = BFS(src, dest, graph);
        if (!graph.containsNode(src) && !graph.containsNode(dest)) {
            res += "unknown: " + src;
            res += "\nunknown: " + dest;
        } else if (!graph.containsNode(src)) {
            res += "unknown: " + src;
        } else if (!graph.containsNode(dest)) {
            res += "unknown: " + dest;
        } else {
            res = "path from " + src + " to " + dest + ":";
            if (bfsList != null) {
                for (LabeledDGraph.Edge<String, String> edge : bfsList) {
                    res += "\n" + src + " to " + edge.getChild() + " via " + edge.getLabel();
                    src = edge.getChild();
                }
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
