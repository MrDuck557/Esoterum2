package esoterum2.world.blocks.binary.memory;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import esoterum2.world.blocks.binary.*;

public class BinaryFlipFlop extends BinaryBlock{

    TextureRegion onHighlight, offHighlight, inputRegion, frontRegion;

    public BinaryFlipFlop(String name){
        super(name);
        rotateHighlight = false;
        outputs = new boolean[]{true, false, false, false};
        inputs = new boolean[]{false, true, true, true};
    }

    @Override
    public void load(){
        super.load();
        inputRegion = Core.atlas.find(name + "-input");
        frontRegion = Core.atlas.find(name + "-front");
        onHighlight = Core.atlas.find(name + "-on");
        offHighlight = Core.atlas.find(name + "-off");
    }

    @Override
    protected TextureRegion[] icons(){
        return new TextureRegion[]{
        baseRegion, offHighlight, inputRegion, frontRegion
        };
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
                if(temp){
                    signal = !signal;
                    propagateSignal();
                }
            }
        }

        @Override
        public void draw(){
            super.draw();
            Draw.color(prevInput ? team.color : Color.white);
            Draw.rect(inputRegion, x, y, rotateHighlight ? rotdeg() : 0);
            Draw.color();
            Draw.rect(frontRegion, x, y, rotdeg());
        }

        @Override
        protected void drawHighlight(){
            Draw.rect((signal ? onHighlight : offHighlight), x, y, rotateHighlight ? rotdeg() : 0);
        }
    }
}
