package esoterum2.world.blocks.binary;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.io.*;
import esoterum2.*;
import mindustry.gen.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;

public class BinaryBlock extends Block{

    public boolean[] outputs;
    public boolean[] inputs;

    public TextureRegion baseRegion, highlightRegion;

    public BinaryBlock(String name){
        super(name);
        rotate = true;
        update = true;
        solid = true;
        destructible = true;
        hideDetails = false;
        buildVisibility = BuildVisibility.shown;
        category = Category.logic;
    }

    @Override
    public void load(){
        super.load();
        baseRegion = Core.atlas.find("esoterum-duck-base");
        highlightRegion = Core.atlas.find(name + "-highlight");
    }

    @Override
    public boolean canReplace(Block other){
        if(other.alwaysReplace) return true;
        return (other != this || rotate) && other instanceof BinaryBlock && size == other.size;
    }

    public static abstract class BinaryBuild extends Building{
        public boolean signal;
        public boolean shouldPropagate;

        @Override
        public void updateTile(){
            if(shouldPropagate){
                propagateSignal();
            }
        }

        public void propagateSignal(){
            shouldPropagate = false;
            for(int i = 0; i < 4; i++){
                if(((BinaryBlock)block).outputs[i] && nearby(i) instanceof BinaryBuild b && inputValid(Utils.relativeDir(this, b))){
                    try{
                        b.updateSignal();
                    }catch(StackOverflowError e){
                        shouldPropagate = true;
                    }
                }
            }
        }

        //implementation left to the block
        //in general, it should call propogateSignal() if the state changed
        public abstract void updateSignal();

        public boolean signal(int dir){
            return signal && ((BinaryBlock)block).outputs[dir];
        }

        @Override
        public void draw(){
            Draw.rect(((BinaryBlock)block).baseRegion, x, y, rotdeg());
            Draw.color(signal ? team.color : Color.white);
            Draw.rect(((BinaryBlock)block).highlightRegion, x, y, rotdeg());
            Draw.color();
        }

        @Override
        public void drawTeam(){
            //no I guess
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            signal = read.bool();
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.bool(signal);
        }

        @Override
        public double sense(LAccess sensor){
            if(sensor == LAccess.enabled) return Mathf.num(signal);
            return super.sense(sensor);
        }

        public boolean inputValid(int dir){
            return ((BinaryBlock)block).inputs[dir];
        }
    }
}
