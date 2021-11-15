package graph.junitTests;

import graph.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.NoSuchElementException;


public class GraphTest {
    @Rule
    public Timeout timeout = Timeout.seconds(10);

    LabeledDGraph<String, String> graph1 = new LabeledDGraph<>();

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  getChildren exception test
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test(expected = NoSuchElementException.class)
    public void testHasException() {
        graph1.addNode("node1");
        graph1.addNode("node2");
        graph1.addEdge("node1", "node2", "edge1");
        graph1.getChildren("node3");
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  containsNode tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void graphHasNode() {
        graph1.addNode("node1");
        assertTrue(graph1.containsNode("node1"));
    }

    @Test
    public void graphHasDifferentNode() {
        graph1.addNode("node2");
        assertFalse(graph1.containsNode("node1"));
    }

    @Test
    public void graphHasNoNode() {
        assertFalse(graph1.containsNode("node1"));
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  size tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void sizeZero() {
        assertEquals(graph1.size(), 0);
    }

    @Test
    public void sizeMultipleNodesAdded() {
        for (int i = 1; i < 7; i++) {
            graph1.addNode("node" + i);
        }
        assertEquals(graph1.size(), 6);
    }

    @Test
    public void sizeOfHugeGraph() {
        for (int i = 0; i < 10000; i++) {
            graph1.addNode("node" + i);
        }
        assertEquals(graph1.size(), 10000);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  isEmpty tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void graphIsEmpty() {
        assertTrue(graph1.isEmpty());
    }

    @Test
    public void graphIsNotEmpty() {
        graph1.addNode("node1");
        graph1.addEdge("node1", "edge", "node2");
        assertFalse(graph1.isEmpty());
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  clear test
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void clearGraph() {
        graph1.addNode("node1");
        graph1.addEdge("node1", "edge1", "node2");
        graph1.addEdge("node2", "edge2", "node3");
        graph1.clear();
        assertTrue(graph1.isEmpty());
    }
}
