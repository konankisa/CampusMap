package graph;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * LabeledDGraph represents a mutable directed graph with a finite number of nodes
 * and edges associated with the nodes. Each node can only appear once in the graph.
 * An edge stores a label and points from 1 node to another. Edges can point to the same node
 * Edges cannot have null values, and neither can nodes
 *
 */
public class LabeledDGraph {

    /**
     * Constructor that creates a HashMap representing an empty graph
     *
     * @spec.effects Constructs an empty HashMap
     */
    public LabeledDGraph() {
    }

    /**
     * Adds a node to the graph if not a duplicate
     *
     * @param node the node to be added to graph
     * @spec.effects Adds node to graph if not a duplicate
     * @spec.requires node != null
     * @return true if node was not already in graph and was added
     */
    public boolean addNode (Node node) {
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
    public boolean addEdge (Node head, Node tail, Edge edge) {
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
    public boolean removeNode (Node node) {
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
    public boolean removeEdge (Node head, Node tail, Edge edge) {
        throw new RuntimeException("removeEdge is not yet implemented");
    }

    /**
     * Checks whether the graph contains given node
     *
     * @param node the node to check for in the graph
     * @return true if the graph doesn't have the node
     */
    public boolean containsNode(Node node) {
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
    public HashSet<Node> getNodes() {
        throw new RuntimeException("getNodes is not yet implemented");
    }

    /**
     * Returns a list of all edges in the graph
     *
     * @return a list of all edges in the graph
     */
    public ArrayList<Edge> getEdges() {
        throw new RuntimeException("getEdges is not yet implemented");
    }

    private class Edge {

        /**
         * Constructor that creates a labeled edge
         *
         * @param label the label of the edge
         * @param child the node that the edge points to
         * @spec.requires label != null, node != null
         * @spec.effects Constructs an edge with a label, pointing to a node child
         */
        public Edge(String label, Node child) {
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
        public Node getChild() {
            throw new RuntimeException("getChild is not implemented");
        }

    }

    private class Node {

        /**
         * Constructor that creates a node
         *
         * @param data the data of the node
         * @spec.requires data != null
         * @spec.effects Constructs a node with some data stored in it
         */
        public Node(String data) {
            throw new RuntimeException("Node is not yet implemented");
        }

        /**
         * Returns the data that is stored in the node
         *
         * @return The data stored in the node
         */
        public String getData() {
            throw new RuntimeException("getData is not yet implemented");
        }
    }

}


