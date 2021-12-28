package esoterum2.content;

import esoterum2.world.blocks.binary.source.*;
import esoterum2.world.blocks.binary.transmission.*;
import mindustry.ctype.*;
import mindustry.world.*;

public class EsoterumDuckBlocks implements ContentList{

    static Block wire, bswitch, brouter; //bswtich because switch is a keyword

    @Override
    public void load(){
        wire = new BinaryWire("wire");
        bswitch = new BinarySwitch("switch");
        brouter = new BinaryRouter("router");
    }
}
