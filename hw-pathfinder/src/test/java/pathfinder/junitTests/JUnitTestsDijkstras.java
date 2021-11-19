package pathfinder.junitTests;

import graph.LabeledDGraph;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static pathfinder.DijkstraAlg.dijkstra;

public class JUnitTestsDijkstras {
    @Rule
    public Timeout timeout = Timeout.seconds(10);

    LabeledDGraph<String, Double> test = new LabeledDGraph<>();

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  dijkstra exception test
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void testHasException() {
        dijkstra(null, "no", test);
        dijkstra("yes", null, test);
        dijkstra("yes", "no", null);
    }

}
