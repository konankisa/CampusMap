package graph;


import java.util.ArrayList;
import java.util.HashMap;
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
     * Holds all the nodes and the edges
     */
    private HashMap<String, ArrayList<Edge>> graphList;

    /**
     * A count of the number of nodes in the graph
     */
    private int size;

    /**
     * Constructor that creates an empty graph
     *
     * @spec.effects Constructs an empty graph
     */
    public LabeledDGraph() {
        this.graphList = new HashMap<>();
        this.size = 0;
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

    /**
     * Removes a node to the graph if present in the graph
     *
     * @param node the node to be removed from graph
     * @spec.effects Removes node from graph if present in the graph
     * @spec.modifies The graph that the node is removed from
     * @spec.requires node != null
     * @return true if node was in the graph and was deleted
     * @throws NoSuchElementException if node is not in graph
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
     * @spec.modifies THe graph that the edge is removed from
     * @spec.requires head != null, tail != null, label != null
     * @return true if edge was removed between 2 nodes
     * @throws NoSuchElementException if edge is not between the given 2 nodes
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
        return size;
    }


    /**
     * Returns a list of all edge labels in the graph
     *
     * @return a list of all edge labels in the graph
     */
    public ArrayList<String> getEdges() {
        throw new RuntimeException("getEdges is not yet implemented");
    }

    // A private class that represents an edge that stores a string label and a string child node
    private class Edge {

        /**
         * Constructor that creates a labeled edge
         *
         * @param label the label of the edge
         * @param child the node that the edge points to
         * @spec.requires label != null, node != null
         * @spec.effects Constructs an edge with a label, pointing to a node child
         */
        public Edge(String label, String child) {
            throw new RuntimeException("Edge is not yet implemented");
        }

        /**
         * Returns the label of the edge
         *
         * @return The label of the edge
         */
        public String getLabel() {
            throw new RuntimeException("getLabel is not yet implemented");
        }

        /**
         * Returns the node that the edge is pointing to
         *
         * @return The child node that the edge points to
         */
        public String getChild() {
            throw new RuntimeException("getChild is not implemented");
        }
    }
}

