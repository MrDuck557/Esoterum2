package esoterum2.world.blocks.binary.transmission;

import esoterum2.world.blocks.binary.*;

//this just defines inputs and outputs to all
//moved updateSignal code to BinaryBlock
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
