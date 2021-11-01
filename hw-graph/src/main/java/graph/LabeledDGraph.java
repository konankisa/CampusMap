package graph;


import java.util.*;

/**
 * LabeledDGraph represents a mutable directed graph with a finite number of nodes
 * and edges associated with the nodes. Each node can only appear once in the graph.
 * A node is a string value stored in the graph.
 * An edge stores a label and points from 1 node to another. Edges can point to the same node.
 *
 */
@SuppressWarnings("unused")
public class LabeledDGraph {
    /**
     * Holds all the nodes and the edges
     */
    private HashMap<String, HashSet<Edge>> graphList;

    // Representation Invariant:
    // graphList != null
    // Nodes cannot be null. The corresponding edge sets to the nodes cannot be null
    // All the edges within the sets cannot be null

    // Abstraction Function:
    // A LabaledDGraph g represents a directed graph with nodes and interconnecting edges
    // connecting the nodes such that g.keySet() is the set of all nodes in the graph, and
    // the list of outgoing edges from a node n is g.get(n). The graph is empty if there are
    // no nodes present in it.

    /**
     * Constructor that creates an empty graph
     *
     * @spec.effects Constructs an empty graph
     */
    public LabeledDGraph() {
        this.graphList = new HashMap<>();
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
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (containsNode(node)) {
            return false;
        }
        graphList.put(node, new HashSet<>());
        return true;
    }

    /**
     * Adds a labeled edge between 2 nodes in the graph.
     * If the head node doesn't exist, add it as a new node
     * and add the edge and tail
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
        if (head == null || tail == null) {
            throw new IllegalArgumentException("A node cannot be null");
        }
        if (edge == null) {
            throw new IllegalArgumentException("Edge cannot be null");
        }
        addNode(head);
        Edge toBeIns = new Edge(edge, tail);
        if (graphList.get(head).contains(toBeIns)) {
            return false;
        }
        graphList.get(head).add(toBeIns);
        return true;
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
    public boolean removeNode(String node) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (!containsNode(node)) {
            throw new NoSuchElementException("Node is not in graph");
        }
        graphList.keySet().remove(node);
        return true;
    }

    /**
     * Removes a labeled edge between 2 nodes in the graph
     *
     * @param head the node the edge will start from
     * @param tail the node the edge will point to
     * @param edge the label of the edge connecting 2 nodes
     * @spec.effects Removes a labeled edge between 2 nodes
     * @spec.modifies THe graph that the edge is removed from
     * @spec.requires head != null, tail != null, label != null
     * @return true if edge was removed between 2 nodes
     * @throws NoSuchElementException if edge is not between the given 2 nodes
     */
    public boolean removeEdge(String head, String edge, String tail) {
        throw new RuntimeException("removeEdge is not yet implemented");
    }

    /**
     * Returns a list of all nodes in the graph
     *
     * @return a list of all nodes in the graph
     */
    public HashSet<String> getNodes() {
        HashSet<String> res = new HashSet<>();
        for (String node : graphList.keySet()) {
            res.add(node);
        }
        return res;
    }

    /**
     * Returns a list of all edge labels in the graph
     *
     * @return a list of all edge labels in the graph
     */
    public HashSet<Edge> getEdges() {
        if (graphList.isEmpty()) {
            return new HashSet<>();
        }
        HashSet<Edge> res = new HashSet<>();
        for (HashSet<Edge> edges : graphList.values()) {
            res.addAll(edges);
        }
        return res;
    }

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
        if (graph.containsNode("me")) {
            System.out.println("me is in the graph");
        }
        System.out.println(graph.getEdges().size());
        System.out.println(graph.isEmpty());
        graph.clear();
        System.out.println(graph.getNodes());
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
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (!containsNode(node)) {
            throw new NoSuchElementException("Node is not in graph");
        }
        Iterator<Edge> edgeIterator = graphList.get(node).iterator();
        ArrayList<String> children = new ArrayList<>();
        while (edgeIterator.hasNext()) {
            children.add(edgeIterator.next().child);
        }
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
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        return graphList.containsKey(node);
    }

    /**
     * Checks whether the graph is empty or not
     *
     * @return true if graph is empty
     */
    public boolean isEmpty() {
        return graphList.isEmpty();
    }

    /**
     * Returns the number of nodes in the graph
     *
     * @return a count of the nodes in the graph
     */
    public int size() {
        return graphList.keySet().size();
    }

    /**
     * Clears the graph completely
     */
    public void clear() {
        graphList.clear();
    }

    // A private class that represents an edge that stores a string label and a string child node
    private class Edge {

        /**
         * A string representing the label of an edge
         */
        private final String label;

        /**
         * A string representing the node that the edge is pointing to
         */
        private final String child;



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
        }

        /**
         * Returns the label of the edge
         *
         * @return The label of the edge
         */
        public String getLabel() {
            return label;
        }

        /**
         * Returns the node that the edge is pointing to
         *
         * @return The child node that the edge points to
         */
        public String getChild() {
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
}

