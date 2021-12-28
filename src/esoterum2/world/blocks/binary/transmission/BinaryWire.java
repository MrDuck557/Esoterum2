package esoterum2.world.blocks.binary.transmission;

import esoterum2.*;
import esoterum2.world.blocks.binary.*;

public class BinaryWire extends BinaryBlock{

    public BinaryWire(String name){
        super(name);
        outputs = new boolean[]{true, false, false, false};
        inputs = new boolean[]{false, true, true, true};
    }

    public static class BinaryWireBuild extends BinaryBuild{

        @Override
        public void updateSignal(){
            boolean temp = signal;
            signal = false;
            for(int i = 1; i < 4; i++){
                if(nearby(i) instanceof BinaryBuild b){
                    signal |= b.signal(Utils.relativeDir(b, this));
                }
            }
            if(temp != signal){
                propagateSignal();
            }
        }
    }
}
