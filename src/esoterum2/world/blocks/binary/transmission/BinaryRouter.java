package esoterum2.world.blocks.binary.transmission;

import esoterum2.*;
import esoterum2.world.blocks.binary.*;

public class BinaryRouter extends BinaryBlock{
    public BinaryRouter(String name){
        super(name);
        outputs = new boolean[]{true, true, true, true};
        inputs = new boolean[]{true, true, true, true};
        rotate = false;
    }

    public class BinaryRouterBuild extends BinaryBuild{

    }
}
