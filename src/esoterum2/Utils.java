package esoterum2;

import mindustry.gen.*;

public class Utils{
    /*public static int relativeDir(Building from, Building to){
        return (from.relativeTo(to) + 4 - to.rotation) % 4;
    }*/

    public static int relativeDir(Building from, Building to){
        if(from == null || to == null) return -1;
        if(from.x == to.x && from.y > to.y) return (7 - from.rotation) % 4;
        if(from.x == to.x && from.y < to.y) return (5 - from.rotation) % 4;
        if(from.x > to.x && from.y == to.y) return (6 - from.rotation) % 4;
        if(from.x < to.x && from.y == to.y) return (4 - from.rotation) % 4;
        return -1;
    }
}