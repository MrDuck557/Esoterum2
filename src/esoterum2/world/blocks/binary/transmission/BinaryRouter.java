package esoterum2.world.blocks.binary.transmission;

import esoterum2.world.blocks.binary.*;

//this just defines inputs and outputs to all
//moved updateSignal code to BinaryBlock
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