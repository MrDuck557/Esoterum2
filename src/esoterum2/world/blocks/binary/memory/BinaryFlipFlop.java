package esoterum2.world.blocks.binary.memory;

import esoterum2.world.blocks.binary.*;

public class BinaryFlipFlop extends BinaryBlock{
    public BinaryFlipFlop(String name){
        super(name);
        rotateHighlight = false;
        outputs = new boolean[]{true, false, false, false};
        inputs = new boolean[]{false, true, true, true};
    }

    public class BinaryFlipFlopBuild extends BinaryBuild{
        boolean prevInput = false;

        @Override
        public void updateSignal(){
            boolean temp = false;
            for(int i = 1; i < 4; i++){
                if(multiB(i) instanceof BinaryBuild b){
                    temp |= b.signal(this);
                }
            }
            if(temp != prevInput){
                prevInput = temp;
                if (temp){
                    signal = !signal;
                    propagateSignal();
                }
            }
        }
    }
}
