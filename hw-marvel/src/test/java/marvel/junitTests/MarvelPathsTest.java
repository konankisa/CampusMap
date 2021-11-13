package marvel.junitTests;

import graph.LabeledDGraph;
import marvel.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static marvel.MarvelPaths.BFS;
import static marvel.MarvelPaths.pathsGraph;
import static org.junit.Assert.*;


public class MarvelPathsTest {
    @Rule
    public Timeout timeout = Timeout.seconds(10);

    LabeledDGraph test = new LabeledDGraph();

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  pathsGraph exception test
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void testHasException() {
        pathsGraph(null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  BFS exception test
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void testGraphHasException() {
        BFS(null, "no", test);
        BFS("yes", null, test);
        BFS("yes", "no", null);
    }
}
