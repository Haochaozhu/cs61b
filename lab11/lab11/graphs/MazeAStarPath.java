package lab11.graphs;


import edu.princeton.cs.algs4.IndexMaxPQ;
import edu.princeton.cs.algs4.IndexMinPQ;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int sourceX = maze.toX(v);
        int sourceY = maze.toY(v);

        int targetX = maze.toX(t);
        int targetY = maze.toY(t);

        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        /* You do not have to use this method. */
        return -1;
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        marked[s] = true;
    }

    @Override
    public void solve() {
        astar(s);
    }

}

