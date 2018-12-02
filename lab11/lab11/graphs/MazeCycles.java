package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    boolean hasCycle;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
    }

    private void dfs(Maze m, int v, int u) {
        if (hasCycle) return;
        marked[v] = true;
        announce();
        for (int w : m.adj(v)) {
            if (!marked[w]) {
                dfs(m, w, v);
            } else if (w != u) {

                edgeTo[v] = u;
                edgeTo[w] = v;
                announce();
                hasCycle = true;
            }
        }
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(maze, 0, 0);
    }

    // Helper methods go here
}

