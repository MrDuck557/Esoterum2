package esoterum2;

import mindustry.gen.*;

public class Utils{
    public static int relativeDir(Building from, Building to){
        return (from.relativeTo(to) + 4 - from.rotation) % 4;
    }
}