package graph;


import java.util.*;

/**
 * LabeledDGraph represents a mutable directed graph with a finite number of nodes
 * and edges associated with the nodes. Each node can only appear once in the graph.
 * A node is a value stored in the graph.
 * An edge stores a label and points from 1 node to another. Edges can point to the same node.
 * This class is generic over the type of data that labels the node and its edges.
 * @param <T> represents a node type
 * @param <E> represents an edge type
 */
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class LabeledDGraph<T, E> {

    /**
     * Holds all the nodes and the edges
     */
    private HashMap<T, HashSet<Edge<T, E>>> graph;

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
            for (T node : getNodes()) {
                assert (node != null) : "Node == null";
                assert (graph.get(node) != null) : "Edge set == null";
                for (Edge<T, E> edge : graph.get(node)) {
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
    public boolean addNode(T node) {
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
    public boolean addEdge(T head, E edge, T tail) {
        checkRep();
        if (head == null || tail == null) {
            throw new IllegalArgumentException("A node cannot be null");
        }
        if (edge == null) {
            throw new IllegalArgumentException("Edge cannot be null");
        }
        addNode(head);
        addNode(tail);
        Edge<T, E> toBeIns = new Edge<>(edge, tail);
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
    public HashSet<T> getNodes() {
        checkRep();
        HashSet<T> res = new HashSet<>();
        for (T node : graph.keySet()) {
            res.add(node);
        }
        checkRep();
        return res;
    }

    /**
     * Returns a list of all edge labels of the given node
     *
     * @param node the node whose edges will be returned
     * @spec.requires node != null
     * @return a list of all edge labels of the given node
     */
    public HashSet<E> getEdges(T node) {
        checkRep();
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (graph.isEmpty()) {
            return new HashSet<>();
        }
        HashSet<E> finalRes = new HashSet<>();
        for (Edge<T, E> edge : graph.get(node)) {
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
    public HashSet<Edge<T, E>> getChildren(T node) {
        checkRep();
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (!containsNode(node)) {
            throw new NoSuchElementException("Node is not in graph");
        }
        Iterator<Edge<T, E>> edgeIterator = graph.get(node).iterator();
        HashSet<Edge<T, E>> children = new HashSet<>();
        while (edgeIterator.hasNext()) {
            children.add(edgeIterator.next());
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
    public boolean containsNode(T node) {
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
     * A public inner class that represents an edge that stores a label and a child node
     * This class is generic over the type of data that labels the edge label and the child node it is attached to
     *
     * @spec.specfield label: E // The data representing an edge
     * @spec.specfield child: T // The node that the edge is pointing to
     */
    public static class Edge<T, E> {

        /**
         * Data representing the label of an edge
         */
        private final E label;

        /**
         * Data representing the node that the edge is pointing to
         */
        private final T child;

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
        public Edge(E label, T child) {
            this.label = label;
            this.child = child;
            checkRep();
        }

        /**
         * Returns the label of the edge
         *
         * @return The label of the edge
         */
        public E getLabel() {
            checkRep();
            E res = label;
            return res;
        }

        /**
         * Returns the node that the edge is pointing to
         *
         * @return The child node that the edge points to
         */
        public T getChild() {
            checkRep();
            T res = child;
            return res;
        }

        /**
         * Standard hashcode function
         *
         * @return hashcode of the current edge
         */
        @Override
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
        @Override
        public boolean equals (Object o) {
            if (!(o instanceof Edge<?, ?>)) {
                return false;
            }
            Edge<?, ?> res = (Edge<?, ?>) o;
            return res.getLabel().equals(this.getLabel())
                    && res.getChild().equals(this.getChild());
        }

        /**
         * Compares this object with the given object for order
         *
         * @param o the object being compared
         * @return a negative, 0, or positive number if this is less than, equal to, or greater than given object

        @Override
        public int compareTo(Edge<E, T> o) {
            if (!this.getChild().equals(o.getChild())) {
                return this.getChild().compareTo(o.getChild());
            }
            if (!this.getLabel().equals(o.getLabel())) {
                return this.getLabel().compareTo(o.getLabel());
            }
            return 0;
        }*/
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

