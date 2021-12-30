package esoterum2.world.blocks.binary.transmission;

import esoterum2.*;
import esoterum2.world.blocks.binary.*;

public class BinaryWire extends BinaryBlock{

    public BinaryWire(String name){
        super(name);
        outputs = new boolean[]{true, false, false, false};
        inputs = new boolean[]{false, true, true, true};
        largeConnections = true;
    }

    public class BinaryWireBuild extends BinaryBuild{

    }
}
