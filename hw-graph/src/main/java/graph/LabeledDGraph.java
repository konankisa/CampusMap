package graph;


import java.util.ArrayList;
import java.util.HashSet;

/**
 * LabeledDGraph represents a mutable directed graph with a finite number of nodes
 * and edges associated with the nodes. Each node can only appear once in the graph.
 * A node is a string value stored in the graph.
 * An edge stores a label and points from 1 node to another. Edges can point to the same node.
 * Edges cannot have null values, and neither can nodes.
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
     * @spec.requires head != null, tail != null, label != null
     * @return true if edge was added between 2 nodes
     */
    public boolean addEdge (String head, String tail, String edge) {
        throw new RuntimeException("addEdge is not yet implemented");
    }

    /**
     * Removes a node to the graph if present in the graph
     *
     * @param node the node to be removed from graph
     * @spec.effects Removes node from graph if present in the graph
     * @spec.requires node != null
     * @return true if node was in the graph and was deleted
     * @throws IllegalArgumentException if node is not in graph
     */
    public boolean removeNode (String node) {
        throw new RuntimeException("removeNode is not yet implemented");
    }

    /**
     * Removes a labeled edge between 2 nodes in the graph
     *
     * @param head the node the edge will start from
     * @param tail the node the edge will point to
     * @param edge the edge connecting 2 nodes
     * @spec.effects Removes a labeled edge between 2 nodes
     * @spec.requires head != null, tail != null, label != null
     * @return true if edge was removed between 2 nodes
     */
    public boolean removeEdge (String head, String tail, String edge) {
        throw new RuntimeException("removeEdge is not yet implemented");
    }

    /**
     * Checks whether the graph contains given node
     *
     * @param node the node to check for in the graph
     * @return true if the graph doesn't have the node
     */
    public boolean containsNode(String node) {
        throw new RuntimeException("containsNode is not yet implemented");
    }

    /**
     * Checks whether the graph is empty or not
     *
     * @return true if graph is empty
     */
    public boolean isEmpty() {
        throw new RuntimeException("isEmpty is not yet implemented");
    }

    /**
     * Returns the number of nodes in the graph
     *
     * @return a count of the nodes in the graph
     */
    public int size() {
        throw new RuntimeException("size is not yet implemented");
    }

    /**
     * Returns a list of all nodes in the graph
     *
     * @return a list of all nodes in the graph
     */
    public HashSet<String> getNodes() {
        throw new RuntimeException("getNodes is not yet implemented");
    }

    /**
     * Returns a list of all edge labels in the graph
     *
     * @return a list of all edge labels in the graph
     */
    public ArrayList<String> getEdges() {
        throw new RuntimeException("getEdges is not yet implemented");
    }
}

