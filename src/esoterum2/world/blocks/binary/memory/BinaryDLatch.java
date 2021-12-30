package esoterum2.world.blocks.binary.memory;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import esoterum2.world.blocks.binary.*;

public class BinaryDLatch extends BinaryBlock{

    public TextureRegion inputRegion, frontRegion;

    public BinaryDLatch(String name){
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
    }

    @Override
    protected TextureRegion[] icons(){
        return new TextureRegion[]{
        baseRegion, highlightRegion, inputRegion, frontRegion
        };
    }

    public class BinaryDLatchBuild extends BinaryBuild{

        boolean side = false;
        boolean back = false;

        @Override
        public void updateSignal(){
            side = (left() instanceof BinaryBuild l && l.signal(this) ||
            right() instanceof BinaryBuild r && r.signal(this));
            if(back() instanceof BinaryBuild b && b.signal(this)){
                back = true;
                boolean temp = signal;
                signal = side;
                if(temp != signal){
                    propagateSignal();
                }
            }else{
                back = false;
            }
        }

        @Override
        public void draw(){
            super.draw();
            Draw.color(side || back ? team.color : Color.white);
            Draw.rect(inputRegion, x, y, rotateHighlight ? rotdeg() : 0);
            Draw.color();
            Draw.rect(frontRegion, x, y, rotdeg());
        }
    }
}
