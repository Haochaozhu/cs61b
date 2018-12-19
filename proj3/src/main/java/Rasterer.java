import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
         System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        if (params.get("ullon") >= MapServer.ROOT_LRLON || params.get("lrlon") <= MapServer.ROOT_ULLON ||
            params.get("ullat") <= MapServer.ROOT_LRLAT || params.get("lrlat") >= MapServer.ROOT_ULLAT) {
            System.out.println("Out of boundary, nothing to show!");
        } else {
            boolean query_success = true;
            double queryLongDPP = (params.get("lrlon") - params.get("ullon")) / params.get("w");
            int depth = calculateDepth(queryLongDPP);
            double[] xTiles = getXTiles(depth);
            double[] yTiles = getYTiles(depth);
            int[] rasterXTiles = getXRasterTiles(params.get("ullon"), params.get("lrlon"), xTiles);
            int[] rasterYTiles = getYRasterTiles(params.get("lrlat"), params.get("ullat"), yTiles);
            String[][] render_grid = buildGrid(rasterXTiles, rasterYTiles, depth);
            results.put("render_grid", render_grid);
            results.put("query_success", query_success);
            results.put("raster_ul_lon", xTiles[rasterXTiles[0]]);
            results.put("raster_ul_lat", yTiles[rasterYTiles[0]]);
            results.put("raster_lr_lon", xTiles[rasterXTiles[rasterXTiles.length - 1]] + xTiles[1] - xTiles[0]);
            results.put("raster_lr_lat", yTiles[rasterYTiles[rasterYTiles.length - 1]] - (yTiles[0] - yTiles[1]));
            results.put("depth", depth);
        }
        return results;
    }

    private String[][] buildGrid(int[] rasterXTiles, int[] rasterYTiles, int depth) {
        String[][] render_grid = new String[rasterYTiles.length][rasterXTiles.length];
        for (int i = 0 ; i < rasterYTiles.length; i += 1) {
            for (int j = 0; j < rasterXTiles.length; j += 1) {
                render_grid[i][j] = "d" + depth + "_" + "x" + rasterXTiles[j] + "_" + "y" + rasterYTiles[i] + ".png";
            }
        }
        return render_grid;
    }
    /* Returns the number of X tiles that comprise of the render grid. For example, if the returned array has
    * 84, 85, 86. Render grid should be x84, x85, x86*/
    private int[] getXRasterTiles(double ullon, double lrlon, double[] xTiles) {
        int start = 0;
        int end = 0;
        for (int i = 0; i < xTiles.length - 1; i += 1) {
            if (ullon >= xTiles[i] && ullon < xTiles[i + 1]) {
                start = i;
            }
        }
        if (ullon >= xTiles[xTiles.length - 1] && ullon < MapServer.ROOT_LRLON) start = xTiles.length - 1;

        for (int i = xTiles.length - 1; i > 0; i -= 1) {
            if (lrlon <= xTiles[i] && lrlon > xTiles[i - 1]) end = i - 1;
        }
        if (lrlon > xTiles[xTiles.length - 1]) end = xTiles.length - 1;

//        System.out.println("Start: " +start);
//        System.out.println("End: " + end);

        if (start == end) {
            int[] res = {start};
            return res;

        } else {
            int[] res = new int[end - start + 1];
            for (int i = 0; i < res.length; i += 1) {
                res[i] = start + i;
            }
            return res;
        }
    }

    private int[] getYRasterTiles(double lrlat, double ullat, double[] yTiles) {
        int up = 0;
        int down = 0;

        for (int i = 0; i < yTiles.length - 1; i += 1) {
            if (ullat <= yTiles[i] && ullat > yTiles[i + 1]) up = i;
        }
        if (ullat <= yTiles[yTiles.length - 1]) up = yTiles.length - 1;

        for (int i = yTiles.length - 1; i >= 0; i -= 1) {
            if (lrlat > yTiles[i] && lrlat <= yTiles[i - 1]) down  = i - 1;
        }

        if (lrlat < yTiles[yTiles.length - 1]) down = yTiles.length - 1;

        if (up == down) {
            int[] res = {up};
            return res;
        } else {
            int[] res = new int[down - up + 1];
            for (int i = 0; i < res.length; i += 1) {
                res[i] = up + i;
            }
            return res;
        }
    }
    
    /*  Given a query lonDPP, calculate a depth that has the largest lonDPP lower than or equal to the given lonDPP.
        Depth 0 has the highest lonDPP, whereas depth 7 has the lowest lonDPP. If the given lonDPP is smaller than depth
        7's lonDPP, return 7. If the given lonDPP is bigger that depth 0's lonDPP, return 0.
    */
    private int calculateDepth(double querylonDPP) {
        double[] depths = new double[8];
        for (int i = 0; i < 8; i += 1) {
            depths[i] = getLonDPP(i);
            if (querylonDPP >= depths[i]) return i;
        }
        return 7;
    }

    private double getLonDPPHelper(double lrLon, double ulLong, double pixels) {
        return (lrLon - ulLong) / pixels;
    }

    private double getLonDPP(int depth) {
        double lonDPP0 =  (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
        return lonDPP0 / Math.pow(2, depth);
    }

    /* Based on the input depth, get an array of tiles with ullon. */
    private double[] getXTiles(int depth) {
        double toAdd = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / Math.pow(2, depth);
        double[] res = new double[(int)Math.pow(2, depth)];
        for (int i= 0; i < res.length; i += 1) {
            res[i] = MapServer.ROOT_ULLON + toAdd * i;
        }
        return res;
    }

    private double[] getYTiles(int depth) {
        double toAdd = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / Math.pow(2, depth);
        double[] res = new double[(int)Math.pow(2,depth)];
        for (int i = 0; i < res.length; i += 1) {
            res[i] = MapServer.ROOT_ULLAT - toAdd * i;
        }
        return  res;
    }

    public static void main(String[] args) {
        Rasterer r = new Rasterer();

        int depth = r.calculateDepth((-122.20908713544797 + 122.3027284165759) / 305);
        System.out.println(depth);
        double[] y = r.getYTiles(depth);
        double[] x = r.getXTiles(depth);

        int[] y1 = r.getYRasterTiles(37.848731523430196, 37.88708748276975, y);
        int[] x1= r.getXRasterTiles(-122.3027284165759, -122.20908713544797, x);
        System.out.println("ullon: " + x[x1[0]]);
        double result = x[x1[x1.length - 1]] + x[1] -x[0];
        System.out.println("lrlon: " + result);
        System.out.println("ullat: " + y[y1[0]]);
        double haha = y[y1[y1.length - 1]] - (y[0] - y[1]);
        System.out.println("lrlat: " + haha);
        String[][] grid = r.buildGrid(x1, y1, depth);
        for (int i = 0; i < grid.length; i += 1) {
            for (int j = 0; j < grid[0].length; j += 1) {
                System.out.println(grid[i][j]);
            }
        }
    }

}
