package byog.Core;

/*Hallways can be considered as rooms. There are three types of hallways:
1. horizontal hallways: rooms with height 3 (include walls).
2. Vertical hallways : rooms with width 3.
3. 'L' shape hallways: A horizontal hallway intersects with a vertical hallway.*/

import java.util.Set;

public class HallWay {
    public Set<Position> pSet;

    public HallWay(Set<Position> s) {
        pSet = s;
    }
}
