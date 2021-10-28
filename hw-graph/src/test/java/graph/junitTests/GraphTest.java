package graph.junitTests;

import graph.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.NoSuchElementException;


public class GraphTest {
    @Rule
    public Timeout timeout = Timeout.seconds(10);

    LabeledDGraph graph1 = new LabeledDGraph();

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  getChildren test
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test(expected = NoSuchElementException.class)
    public void testHasException() {
        graph1.addNode("node1");
        graph1.addNode("node2");
        graph1.addEdge("node1", "node2", "edge1");
        graph1.getChildren("node3");
    }
}
