package lab11.graphs;


import edu.princeton.cs.algs4.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);

    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int v) {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> queue = new Queue<>();

        marked[v] = true;
        distTo[v] = 0;
        edgeTo[v] = v;

        announce();

        queue.enqueue(v);

        while (!queue.isEmpty()) {
            int x = queue.dequeue();
            for (int w: maze.adj(x)) {
                if (!marked[w]) {
                    marked[w] = true;
                    distTo[w] = distTo[x] + 1;
                    edgeTo[w] = x;
                    announce();

                    if (w == t) return;
                    queue.enqueue(w);
                }
            }
        }

    }


    @Override
    public void solve() {
       bfs(s);
    }
}

