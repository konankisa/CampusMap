package marvel;

import graph.LabeledDGraph;

import java.util.*;


// Static methods, doesn't store data, main method runs the class (no instances of MarvelPaths being called)

/**
 * This class creates a graph based on Marvel characters and the connections between them via their appearances
 * in comic books. One method creates the actual graph of characters connected by the comic books they appear in,
 * while the other method returns the shortest path of connection between 2 characters in the graph
 */
public class MarvelPaths {
    // This class does not have an abstraction function or a rep invariant because it is not an ADT, where we
    // create instances of it and store data. Rather, this class only contains static methods that take in some input
    // and return some value, however, they do not store any information given to them. Furthermore, this class
    // contains a main method where we call the methods of the class, whereas for an ADT we create an instance of
    // it, then call its methods on that instance.

    /**
     * Creates a LabeledDGraph of characters and the books they appear in as edges and nodes respectively
     *
     * @param filename the name of the file with the data of characters and books
     * @spec.requires filename != null
     * @return a LabeledDGraph of characters and their books
     */
    public static LabeledDGraph<String, String> pathsGraph(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("File name cannot be null");
        }
        LabeledDGraph<String, String> marvelPaths = new LabeledDGraph<>();
        HashMap<String, ArrayList<String>> parsedList = MarvelParser.parseData(filename);
        for (String book : parsedList.keySet()) {
            ArrayList<String> curBookChars = parsedList.get(book);
            int i = 1;
            for (String curChar : curBookChars) {
                List<String> childChars = curBookChars.subList(i, curBookChars.size());
                for (String childChar : childChars) {
                    if (!curChar.equals(childChar)) {
                        marvelPaths.addEdge(curChar, book, childChar);
                        marvelPaths.addEdge(childChar, book, curChar);
                    }
                }
                i++;
            }
        }
        return marvelPaths;
    }

    /**
     * Creates a list that contains the shortest path between 2 characters in the graph
     *
     * @param start the first character to check
     * @param end the second character to whom the shortest path exists
     * @param graph the graph in which the shortest path between 2 characters will be traced
     * @spec.requires graph != null, start != null, end != null
     * @return the shortest path between 2 characters
     */
    public static ArrayList<LabeledDGraph.Edge<String, String>> BFS(String start, String end, LabeledDGraph<String, String> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start or end nodes cannot be null");
        }
        if (graph.containsNode(start) && graph.containsNode(end)) {
            Queue<String> path = new LinkedList<>();
            Map<String, ArrayList<LabeledDGraph.Edge<String, String>>> visited = new HashMap<>();
            path.add(start);
            visited.put(start, new ArrayList<>());
            while (!path.isEmpty()) {
                String next = path.remove();
                if (next.equals(end)) {
                    ArrayList<LabeledDGraph.Edge<String, String>> res = new ArrayList<>(visited.get(next));
                    return res;
                }
                TreeSet<LabeledDGraph.Edge<String, String>> children = new TreeSet<>((o1, o2) -> {
                    if (!o1.getChild().equals(o2.getChild())) {
                        return o1.getChild().compareTo(o2.getChild());
                    }
                    if (!o1.getLabel().equals(o2.getLabel())) {
                        return o1.getLabel().compareTo(o2.getLabel());
                    }
                    return 0;
                });
                children.addAll(graph.getChildren(next));
                for (LabeledDGraph.Edge<String, String> child : children) {
                    if (!visited.containsKey(child.getChild())) {
                        ArrayList<LabeledDGraph.Edge<String, String>> curPath = visited.get(next);
                        ArrayList<LabeledDGraph.Edge<String, String>> newPath = new ArrayList<>(curPath);
                        newPath.add(child);
                        visited.put(child.getChild(), newPath);
                        path.add(child.getChild());
                    }
                }
            }
            return null;
        }
        return null;
    }

    /**
     * The entry point to the class
     * @param args the command-line arguments provided to the system
     */
    public static void main (String[] args) {
        LabeledDGraph<String, String> marvelGraph = pathsGraph("marvel.csv");
        Scanner input = new Scanner(System.in);
        String res = "";
        System.out.println("Enter the names of the characters in all caps, with hyphens in between words");
        System.out.println("Enter the name of the first character: ");
        String src = input.nextLine();
        System.out.println("Enter the name of the second character: ");
        String dest = input.nextLine();
        ArrayList<LabeledDGraph.Edge<String, String>> bfsList = BFS(src, dest, marvelGraph);
        if (!marvelGraph.containsNode(src) && !marvelGraph.containsNode(dest)) {
            res += "unknown: " + src;
            res += "\nunknown: " + dest;
        } else if (!marvelGraph.containsNode(src)) {
            res += "unknown: " + src;
        } else if (!marvelGraph.containsNode(dest)) {
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
        System.out.println(res);
        input.close();
    }
}
