package esoterum2.world.blocks.binary.source;

import arc.math.*;
import esoterum2.world.blocks.binary.*;
import mindustry.logic.*;

public class BinarySwitch extends BinaryBlock{
    public BinarySwitch(String name){
        super(name);
        rotate = false;
        configurable = true;
        outputs = new boolean[]{true, true, true, true};
        inputs = new boolean[]{false, false, false, false};
    }

    public class BinarySwitchBuild extends BinaryBuild{
        @Override
        public boolean configTapped(){
            signal = !signal;
            propagateSignal();
            return false;
        }

        @Override
        public void updateSignal(){
            //eh
            //should not be called
        }

        @Override
        public void control(LAccess type, double p1, double p2, double p3, double p4){
            if(type == LAccess.enabled){
                boolean temp = !Mathf.zero((float)p1);
                if(temp != signal){
                    signal = temp;
                    propagateSignal();
                }
            }
        }
    }
}
