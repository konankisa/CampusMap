package graph;


import java.util.*;

/**
 * LabeledDGraph represents a mutable directed graph with a finite number of nodes
 * and edges associated with the nodes. Each node can only appear once in the graph.
 * A node is a string value stored in the graph.
 * An edge stores a label and points from 1 node to another. Edges can point to the same node.
 * No 2 edges with the same label can point to the same child
 *
 * @spec.specfield graph: HashMap<String, HashSet<Edge>> // A graph that store nodes and their
 *                                                           // corresponding sets of edges
 */
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class LabeledDGraph {
    /**
     * Holds all the nodes and the edges
     */
    private HashMap<String, HashSet<Edge>> graph;

    /**
     * Toggles the expensive checkRep() computations
     */
    private final boolean DEBUG = false;

    // Representation Invariant:
    // graph != null
    // Nodes cannot be null. The corresponding edge sets to the nodes cannot be null
    // All the edges within the sets cannot be null

    // Abstraction Function:
    // A LabeledDGraph g represents a directed graph with nodes and interconnecting edges
    // connecting the nodes such that g.keySet() is the set of all nodes in the graph, and
    // the list of outgoing edges from a node n is g.get(n). The graph is empty if there are
    // no nodes present in it.

    /**
     * Constructor that creates an empty graph
     *
     * @spec.effects Constructs an empty graph
     */
    public LabeledDGraph() {
        this.graph = new HashMap<>();
        checkRep();
    }

    /**
     * Throws an exception if the representation invariant is violated
     */
    private void checkRep() {
        assert (graph != null) : "graph == null";
        if (DEBUG) {
            for (String node : getNodes()) {
                assert (node != null) : "Node == null";
                assert (graph.get(node) != null) : "Edge set == null";
                for (Edge edge : graph.get(node)) {
                    assert (edge != null) : "Edge == null";
                }
            }
        }
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
    public boolean addNode(String node) {
        checkRep();
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (containsNode(node)) {
            return false;
        }
        graph.put(node, new HashSet<>());
        checkRep();
        return true;
    }

    /**
     * Adds a labeled edge between 2 nodes in the graph.
     * If the head node doesn't exist, add it as a new node
     * and add the edge and tail. If the tail node doesn't exist,
     * add it as a child node to the edge
     *
     * @param head the node the edge will start from
     * @param tail the node the edge will point to
     * @param edge the label of the edge connecting 2 nodes
     * @spec.effects Adds a labeled edge between 2 nodes
     * @spec.modifies The graph that the edge is added to
     * @spec.requires head != null, tail != null, label != null
     * @return true if edge was added between 2 nodes
     */
    public boolean addEdge(String head, String edge, String tail) {
        checkRep();
        if (head == null || tail == null) {
            throw new IllegalArgumentException("A node cannot be null");
        }
        if (edge == null) {
            throw new IllegalArgumentException("Edge cannot be null");
        }
        addNode(head);
        addNode(tail);
        Edge toBeIns = new Edge(edge, tail);
        if (graph.get(head).contains(toBeIns)) {
            return false;
        }
        graph.get(head).add(toBeIns);
        checkRep();
        return true;
    }

    /**
     * Returns a list of all nodes in the graph
     *
     * @return a list of all nodes in the graph
     */
    public HashSet<String> getNodes() {
        checkRep();
        HashSet<String> res = new HashSet<>();
        for (String node : graph.keySet()) {
            res.add(node);
        }
        checkRep();
        return res;
    }

    /**
     * Returns a list of all edge labels in the graph
     *
     * @return a list of all edge labels in the graph
     */
    public HashSet<String> getEdges() {
        checkRep();
        if (graph.isEmpty()) {
            return new HashSet<>();
        }
        HashSet<Edge> res = new HashSet<>();
        HashSet<String> finalRes = new HashSet<>();
        for (HashSet<Edge> edges : graph.values()) {
            res.addAll(edges);
        }
        for (Edge edge : res) {
            finalRes.add(edge.getLabel());
        }
        checkRep();
        return finalRes;
    }

    /**
     * Returns a list of child nodes, with their edges, of the parent in the graph
     *
     * @param node the parent node whose children should be returned
     * @spec.requires node != null
     * @return a list of all child nodes of the parent node
     * @throws NoSuchElementException if node is not in graph
     */
    public HashSet<String> getChildren(String node) {
        checkRep();
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (!containsNode(node)) {
            throw new NoSuchElementException("Node is not in graph");
        }
        Iterator<Edge> edgeIterator = graph.get(node).iterator();
        HashSet<String> children = new HashSet<>();
        while (edgeIterator.hasNext()) {
            Edge curChild = edgeIterator.next();
            children.add(" " + curChild.getChild() + "(" + curChild.getLabel() + ")");
        }
        checkRep();
        return children;
    }

    /**
     * Checks whether the graph contains given node
     *
     * @param node the node to check for in the graph
     * @spec.requires node != null
     * @return true if the graph doesn't have the node
     */
    public boolean containsNode(String node) {
        checkRep();
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        return graph.containsKey(node);
    }

    /**
     * Checks whether the graph is empty or not
     *
     * @return true if graph is empty
     */
    public boolean isEmpty() {
        checkRep();
        return graph.isEmpty();
    }

    /**
     * Returns the number of nodes in the graph
     *
     * @return a count of the nodes in the graph
     */
    public int size() {
        checkRep();
        return graph.keySet().size();
    }

    /**
     * Clears the graph completely
     */
    public void clear() {
        checkRep();
        graph.clear();
        checkRep();
    }

    /**
     * A public inner class that represents an edge that stores a string label and a string child node
     *
     * @spec.specfield label: String // The string representing an edge
     * @spec.specfield child: String // The node that the edge is pointing to
     */
    private static class Edge {

        /**
         * A string representing the label of an edge
         */
        private final String label;

        /**
         * A string representing the node that the edge is pointing to
         */
        private final String child;

        // Representation Invariant:
        // label != null and child != null

        // Abstraction Function:
        // An Edge e represents a labeled edge without an origin such that
        // e.label = this.label
        // e.child = this.child

        /**
         * Throws an exception if the representation invariant is violated
         */
        private void checkRep() {
            assert (label != null) : "Label == null";
            assert (child != null) : "Child == null";
        }

        /**
         * Constructor that creates a labeled edge
         *
         * @param label the label of the edge
         * @param child the node that the edge points to
         * @spec.requires label != null, node != null
         * @spec.effects Constructs an edge with a label, pointing to a node child
         */
        public Edge(String label, String child) {
            this.label = label;
            this.child = child;
            checkRep();
        }

        /**
         * Returns the label of the edge
         *
         * @return The label of the edge
         */
        public String getLabel() {
            checkRep();
            return label;
        }

        /**
         * Returns the node that the edge is pointing to
         *
         * @return The child node that the edge points to
         */
        public String getChild() {
            checkRep();
            return child;
        }

        /**
         * Standard hashcode function
         *
         * @return hashcode of the current edge
         */
        public int hashCode() {
            return label.hashCode() * child.hashCode();
        }

        /**
         * Standard equality function
         *
         * @param o the object to be compared for equality
         * @return true iff 'o' is an instance of an Edge and 'this' and 'o' represent the same
         * Edge.
         */
        public boolean equals (Object o) {
            if (!(o instanceof Edge)) {
                return false;
            }
            Edge res = (Edge) o;
            return res.getLabel().equals(this.getLabel())
                    && res.getChild().equals(this.getChild());
        }
    }

    /*
        public static void main (String[] args) {
        LabeledDGraph graph = new LabeledDGraph();
        graph.addNode("hi");
        graph.addEdge("hi", "im", "me");
        System.out.println(graph.getNodes());
        graph.addEdge("me", "but", "sad");
        System.out.println(graph.getNodes());
        graph.addEdge("sad", "yet", "happy");
        System.out.println(graph.getNodes());
        HashSet<Edge> edges = graph.getEdges();
        ArrayList<String> labels = new ArrayList<>();
        for (Edge edge : edges) {
            labels.add(edge.label);
        }
        System.out.println(labels);
        System.out.println(graph.removeEdge("hi", "im", "me"));
        HashSet<Edge> edges2 = graph.getEdges();
        for (Edge edge : edges2) {
            labels.add(edge.label);
        }
        System.out.println(labels);
        if (graph.containsNode("me")) {
            System.out.println("me is in the graph");
        }
        System.out.println(graph.getEdges().size());
        System.out.println(graph.isEmpty());
        graph.removeNode("happy");
        System.out.println(graph.getNodes());
    }
     */
}

