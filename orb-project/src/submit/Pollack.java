package submit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import graph.FindState;
import graph.Finder;
import graph.FleeState;
import graph.Node;
import graph.NodeStatus;

/** A solution with find-the-Orb optimized and flee getting out as fast as possible. */
public class Pollack extends Finder {

    /** Get to the orb in as few steps as possible. <br>
     * Once you get there, you must return from the function in order to pick it up. <br>
     */

    @Override
    public void find(FindState state) {
        // Walk to the orb

        // visited is a HashSet that will store the IDs of all the tiles that have been visited
        HashSet<Long> visited= new HashSet<>();

        dfsOrb(state, visited);

    }

    public static void dfsOrb(FindState state, HashSet<Long> visited) {

        if (state.distanceToOrb() == 0) { return; }

        // current is the ID of the tile that Pollack is currently on.
        long current= state.currentLoc();

        visited.add(current);

        List<NodeStatus> neighbors2= new ArrayList<>();
        for (NodeStatus w : state.neighbors()) {
            neighbors2.add(w);
        }
        Collections.sort(neighbors2);

        // for each neighbor's ID in the set of all of Pollack's current neighbors' IDs
        for (NodeStatus w : neighbors2) {

            if (!visited.contains(w.getId())) {
                state.moveTo(w.getId());

                // must place return statement after dfs statement to ensure that code will not move
                // on to line
                // 72 after the return statement
                dfsOrb(state, visited);

                if (state.distanceToOrb() == 0) { return; }

                state.moveTo(current);
            }
        }
    }

    @Override
    public void flee(FleeState state) {
        // Get out of the cavern in time, picking up as much gold as possible.

        // shortestpath = the shortest path from the tile with the orb to the exit orb
        List<Node> shortestpath= Path.shortestPath(state.currentNode(), state.exit());
        moveAlong(state, shortestpath);

    }

    /** Traverse the nodes in moveOut sequentially, starting at the node<br>
     * // pertaining to state <br>
     */
    public void moveAlong(FleeState state, List<Node> moveOut) {

        for (Node node : moveOut) {
            Node current= state.currentNode();
            if (current != node) {
                state.moveTo(node);
            }

        }

    }

}
