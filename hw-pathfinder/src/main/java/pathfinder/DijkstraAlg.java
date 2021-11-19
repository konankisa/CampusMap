package pathfinder;

import graph.LabeledDGraph;
import pathfinder.datastructures.Path;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.TreeSet;

/**
 * This class is used to find the shortest path in terms of edge weight between 2 points using Dijkstra's algorithm.
 * The class contains 1 method, which is the algorithm itself, used to find the shortest path. The behavior of the
 * algorithm is undefined in the case of 2 shortest paths with the same number of hops and the same weights.
 */
public class DijkstraAlg {
    // This class does not have an abstraction function or a rep invariant because it is not an ADT, where we
    // create instances of it and store data. Rather, this class only contains a static method that takes in some input
    // and returns some value, however, it does not store any information given to it.

    /**
     * Creates the shortest path in terms of edge weight between 2 given points in the graph
     *
     * @param start the first point to check
     * @param end the second point to which the shortest path exists
     * @param graph the graph in which the shortest path between 2 characters will be traced
     * @param <T> represents the type of point given and used in the path
     * @spec.requires graph != null, start != null, end != null
     * @return the shortest path in terms of edge weight between the 2 given points
     */
    public static <T> Path<T> dijkstra (T start, T end, LabeledDGraph<T, Double> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start or end nodes cannot be null");
        }
        if (graph.containsNode(start) && graph.containsNode(end)) {
            PriorityQueue<Path<T>> active = new PriorityQueue<>((o1, o2) -> {
                double o1Cost = 0.000;
                double o2Cost = 0.000;
                for (Path<T>.Segment node : o1) {
                    o1Cost += node.getCost();
                }
                for (Path<T>.Segment node : o2) {
                    o2Cost += node.getCost();
                }
                if (o1Cost > o2Cost) {
                    return 1;
                } else if (o1Cost < o2Cost) {
                    return -1;
                }
                return 0;
            });
            HashSet<T> finished = new HashSet<>();
            Path<T> self = new Path<>(start);
            active.add(self.extend(start, 0));
            while (!active.isEmpty()) {
                Path<T> minPath = active.remove();
                T minDest = minPath.getEnd();
                if (minDest.equals(end)) {
                    Path<T> res = minPath;
                    return res;
                }
                if (finished.contains(minDest)) {
                    continue;
                }
                TreeSet<LabeledDGraph.Edge<T, Double>> children = new TreeSet<>((o1, o2) -> {
                    if (!o1.getLabel().equals(o2.getLabel())) {
                        return o1.getLabel().compareTo(o2.getLabel());
                    }
                    return 0;
                });
                children.addAll(graph.getChildren(minDest));
                for (LabeledDGraph.Edge<T, Double> child : children) {
                    if (!finished.contains(child.getChild())) {
                        Path<T> newPath = minPath.extend(child.getChild(), child.getLabel());
                        active.add(newPath);
                    }
                }
                finished.add(minDest);
            }
            return null;
        }
        return null;
    }
}
