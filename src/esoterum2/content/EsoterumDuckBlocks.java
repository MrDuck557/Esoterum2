package esoterum2.content;

import esoterum2.world.blocks.binary.logic.*;
import esoterum2.world.blocks.binary.memory.*;
import esoterum2.world.blocks.binary.source.*;
import esoterum2.world.blocks.binary.transmission.*;
import mindustry.ctype.*;
import mindustry.world.*;

public class EsoterumDuckBlocks implements ContentList{

    static Block bwire, bswitch, brouter, bjunction, bcjunction, bdlatch, bflipflop, bsignalcontroller, bnode,
    bnotgate, bandgate, bxorgate;

    @Override
    public void load(){
        bwire = new BinaryWire("wire");
        bswitch = new BinarySwitch("switch");
        brouter = new BinaryRouter("router");
        bjunction = new BinaryJunction("junction");
        bcjunction = new BinaryJunction("corner-junction"){{
            rotate = true;
            wireConnections = new int[]{1, 0, 3, 2};
        }};
        bdlatch = new BinaryDLatch("dlatch");
        bflipflop = new BinaryFlipFlop("flipflop");
        bsignalcontroller = new BinarySignalController("signal-controller");
        bnode = new BinaryNode("node");

        bnotgate = new BinaryLogicGate("not-gate"){{
            operation = (i) -> i == 0;
            single = true;
        }};
        bandgate = new BinaryLogicGate("and-gate"){{
            operation = (i) -> i == 2;
        }};
        bxorgate = new BinaryLogicGate("xor-gate"){{
            operation = (i) -> i == 1;
        }};
    }
}
