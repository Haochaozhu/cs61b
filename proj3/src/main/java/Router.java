import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */

    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        PriorityQueue<SearchNode> pq = new PriorityQueue<>();
        Map<Long, SearchNode> marked = new LinkedHashMap<>();

        long start = g.closest(stlon, stlat);
        long destination = g.closest(destlon, destlat);

        SearchNode initial = new SearchNode(0,
                g.distance(start, destination), null, start);
        marked.put(initial.id, initial);
        pq.add(initial);

        while(pq.peek().estimatedDistToDes != 0) {
            SearchNode curNode = pq.remove();

            for (Long neigbID : g.adjacent(curNode.id)) {
                if (!marked.containsKey(neigbID)) {
                    SearchNode neighb = new SearchNode(g.distance(neigbID, curNode.id) +
                            curNode.bsDistFromStart, g.distance(neigbID, destination), curNode, neigbID);
                    pq.add(neighb);
                    marked.put(neigbID, neighb);
                } else {
                    SearchNode neighb = marked.get(neigbID);
                    if (g.distance(neigbID, curNode.id) + curNode.bsDistFromStart < neighb.bsDistFromStart) {
                        neighb.bsDistFromStart = g.distance(neigbID, curNode.id) + curNode.bsDistFromStart;
                        neighb.previousNode = curNode;
                        pq.add(new SearchNode(neighb.bsDistFromStart, neighb.estimatedDistToDes,
                                curNode, neigbID));
                    }
                }
            }
        }

        SearchNode end = pq.remove();



        return findPath(end); // FIXME
    }

    private static List<Long> findPath(SearchNode target) {
        LinkedList<Long> res = new LinkedList<>();
        SearchNode currentNode = target;

        while (currentNode.previousNode != null) {
            res.addFirst(currentNode.id);
            currentNode = currentNode.previousNode;
        }

        res.addFirst(currentNode.id);

        return res;
    }

    static class SearchNode implements Comparable<SearchNode> {
        double bsDistFromStart;
        double estimatedDistToDes;
        SearchNode previousNode;
        long id;

        SearchNode(double bsDistFromStart,
                   double estimatedDistToDes, SearchNode previousNode, long id) {
            this.bsDistFromStart = bsDistFromStart;
            this.estimatedDistToDes = estimatedDistToDes;
            this.previousNode = previousNode;
            this.id = id;
        }

        @Override
        public int compareTo(SearchNode o) {
            double thisPriority = this.bsDistFromStart + this.estimatedDistToDes;
            double oPriority = o.bsDistFromStart + o.estimatedDistToDes;
            if (thisPriority == oPriority) return 0;
            else return thisPriority < oPriority ? -1 : 1;
        }
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        LinkedList<NavigationDirection> res = new LinkedList<>();
        String example = "Go straight on unknown road and continue for 0.000 miles.";

        String DIRECTION = "Start";
        String WAY = g.findNode(route.get(0)).way;
        double DISTANCE = g.distance(route.get(0), route.get(1));

        for (int i = 0; i < route.size() - 1; i += 1) {
            GraphDB.Node cur = g.findNode(route.get(i));
            GraphDB.Node next = g.findNode(route.get(i + 1));

            if (!cur.way.equals(next.way)) {
                String s = DIRECTION + " on " + WAY + " and continue for " + DISTANCE + " miles.";
                res.add(NavigationDirection.fromString(s));
                double bearing = g.bearing(route.get(i), route.get(i + 1));
                DIRECTION = decideDirection(bearing);
                WAY = next.way;
                if (i != route.size() - 1) {
                    DISTANCE = g.distance(next.id, route.get(i + 2));
                }
            } else {
                DISTANCE += g.distance(cur.id, next.id);
            }
        }

        return res; // FIXME
    }


    private static String decideDirection(double bearing) {
        if (bearing <= 15 && bearing >= -15) return "Go straight";
        else if (bearing > 15) {
            if (bearing <= 30) return "Slight right";
            else if (bearing > 30 && bearing <= 100) return "Turn right";
            else return "Sharp right";
        }
        else if (bearing < -15) {
            if (bearing >= -30) return "Slight left";
            else if (bearing < -30 && bearing >= -100) return "Turn left";
            else return "Sharp left";
        }
        return null;
    }
    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }

    public static void main(String[] args) {
        String s = "Start" + " on " + "Galloway" + " and continue for " + 0.982 + " miles.";
        NavigationDirection n = NavigationDirection.fromString(s);
        System.out.println(n);
    }
}
