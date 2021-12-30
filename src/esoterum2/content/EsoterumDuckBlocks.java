package esoterum2.content;

import esoterum2.world.blocks.binary.memory.*;
import esoterum2.world.blocks.binary.source.*;
import esoterum2.world.blocks.binary.transmission.*;
import mindustry.ctype.*;
import mindustry.world.*;

public class EsoterumDuckBlocks implements ContentList{

    static Block bwire, bswitch, brouter, bjunction, bcjunction, bdlatch; //bswtich because switch is a keyword

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
    }
}
