package esoterum2.world.blocks.binary.transmission;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import esoterum2.*;
import esoterum2.world.blocks.binary.*;

public class BinaryJunction extends BinaryBlock{

    //where each direction will connect to
    //using an array makes it very flexible
    //draw code will get wack
    public int[] wireConnections;

    public TextureRegion[] wires;
    //which "wire" each signal colors
    public int[] mappings;

    public BinaryJunction(String name){
        super(name);
        outputs = new boolean[]{true, true, true, true};
        inputs = new boolean[]{true, true, true, true};
        wireConnections = new int[]{2, 3, 0, 1};
        rotate = false;
    }

    @Override
    public void load(){
        super.load();
        mappings = new int[4];
        for(int i = 0; i < 4; i++){
            if(wireConnections[i] < i && wireConnections[wireConnections[i]] == i){
                mappings[i] = wireConnections[i];
            }else{
                mappings[i] = i;
            }
        }
        wires = new TextureRegion[4];
        TextureRegion ohno = Core.atlas.find("error");
        for(int i = 0; i < 4; i++){
            wires[i] = Core.atlas.find(name + "-wire-" + i);
            if(wires[i] == ohno){
                wires[i] = null;
            }
        }
    }

    @Override
    protected TextureRegion[] icons(){
        int len = 2;
        for(TextureRegion i : wires){
            if(i != null){
                len++;
            }
        }
        TextureRegion[] out = new TextureRegion[len];
        out[0] = baseRegion;
        out[1] = highlightRegion;
        int index = 2;
        for(TextureRegion i : wires){
            if(i != null){
                out[index++] = i;
            }
        }
        return out;
    }

    public class BinaryJunctionBuild extends BinaryBuild{
        //input signals
        public boolean[] inSignals = new boolean[4];
        //ouput signals
        public boolean[] outSignals = new boolean[4];

        @Override
        public void updateSignal(){
            boolean[] temp = outSignals;
            outSignals = new boolean[4];
            signal = false;
            for(int i = 0; i < 4; i++){
                inSignals[i] = BinaryBlock.signal(multiB(i), this);
                outSignals[wireConnections[i]] = inSignals[i];
                signal |= inSignals[i];
                inSignals[i] = false;
            }
            for(int i = 0; i < 4; i++){
                if(temp[i] != outSignals[i]){
                    propagateSignal();
                    break;
                }
            }
        }

        @Override
        public boolean signal(int dir){
            return outSignals[dir];
        }

        @Override
        public void draw(){
            super.draw();
            boolean temp;
            for(int i = 0; i < 4; i++){
                if(wires[i] != null){
                    temp = false;
                    for(int j = 0; j < 4; j++){
                        if(mappings[j] == i){
                            temp |= inSignals[j];
                        }
                    }
                    Draw.color(temp ? team.color : Color.white);
                    Draw.rect(wires[i], x, y, rotdeg());
                }
            }
            Draw.color();
        }
    }
}