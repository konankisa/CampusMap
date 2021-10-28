package graph;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * LabeledDGraph represents a mutable directed graph with a finite number of nodes
 * and edges associated with the nodes. Each node can only appear once in the graph.
 * A node is a string value stored in the graph.
 * An edge stores a label and points from 1 node to another. Edges can point to the same node.
 * Edges cannot have null values, and neither can nodes.
 * The class currently does not support edge or node removal.
 *
 */
public class LabeledDGraph {

    /**
     * Constructor that creates an empty graph
     *
     * @spec.effects Constructs an empty graph
     */
    public LabeledDGraph() {
        throw new RuntimeException("Graph is not yet implemented");
    }

    /**
     * Adds a node to the graph if not a duplicate
     *
     * @param node the node to be added to graph
     * @spec.effects Adds node to graph if not a duplicate
     * @spec.modifies The graph that the node is added to
     * @spec.requires node != null
     * @return true if node was not already in graph and was added
     */
    public boolean addNode (String node) {
        throw new RuntimeException("addNode is not yet implemented");
    }

    /**
     * Adds a labeled edge between 2 nodes in the graph
     *
     * @param head the node the edge will start from
     * @param tail the node the edge will point to
     * @param edge the edge connecting 2 nodes
     * @spec.effects Adds a labeled edge between 2 nodes
     * @spec.modifies The graph that the edge is added to
     * @spec.requires head != null, tail != null, label != null
     * @return true if edge was added between 2 nodes
     */
    public boolean addEdge (String head, String tail, String edge) {
        throw new RuntimeException("addEdge is not yet implemented");
    }

    /**
     * Returns a list of child nodes of the parent in the graph
     *
     * @param node the parent node whose children should be returned
     * @spec.requires node != null
     * @return a list of all child nodes of the parent node
     * @throws NoSuchElementException if node is not in graph
     */
    public ArrayList<String> getChildren(String node) {
        throw new RuntimeException("getChildren is not yet implemented");
    }

    /**
     * Returns a list of all nodes in the graph
     *
     * @return a list of all nodes in the graph
     */
    public HashSet<String> getNodes() {
        throw new RuntimeException("getNodes is not yet implemented");
    }
}

