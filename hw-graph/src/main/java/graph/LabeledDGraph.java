package graph;


/**
 * LabeledDGraph represents a mutable directed graph with a finite number of nodes
 * and edges associated with the nodes. Each node can only appear once in the graph.
 * An edge stores a label and points from 1 node to another
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
    public boolean addNode (String node) {
        throw new RuntimeException("addNode is not yet implemented");
    }

    public boolean addEdge (String head, String tail, String label) {
        throw new RuntimeException("addEdge is not yet implemented");
    }

    public boolean removeNode (String node) {
        throw new RuntimeException("removeNode is not yet implemented");
    }

    public boolean removeEdge (String head, String tail, String edge) {
        throw new RuntimeException("removeEdge is not yet implemented");
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


