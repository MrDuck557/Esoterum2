package esoterum2.world.blocks.binary.transmission;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import esoterum2.world.blocks.binary.*;
import mindustry.gen.*;

public class BinarySignalController extends BinaryBlock{

    public String[] states = new String[]{"X", "I", "O"};

    public TextureRegion inputRegion, outputRegion;

    public BinarySignalController(String name){
        super(name);
        //neither should actually be used
        outputs = new boolean[]{true, true, true, true};
        inputs = new boolean[]{true, true, true, true};
        configurable = true;

        config(IntSeq.class, (BinarySignalControllerBuild b, IntSeq i) -> {
            b.configs = IntSeq.with(i.items);
            b.updateProximity();
        });

        config(Integer.class, (BinarySignalControllerBuild b, Integer i) -> {
            b.configs.incr(i, -1);
            if(b.configs.get(i) < 0) b.configs.set(i, 2);
            b.updateProximity();
        });
    }

    @Override
    public void load(){
        super.load();
        inputRegion = Core.atlas.find(name + "-in");
        outputRegion = Core.atlas.find(name + "-out");
    }

    public class BinarySignalControllerBuild extends BinaryBuild{
        protected boolean rotInit = false;

        public IntSeq configs = IntSeq.with(0, 0, 0, 0);

        @Override
        public boolean inputValid(int dir){
            return configs.get(dir) == 1;
        }

        @Override
        public boolean outputValid(int dir){
            return configs.get(dir) == 2;
        }

        @Override
        protected void drawConnections(){
            for(int i = 0; i < 4; i++){
                Draw.color((
                (inputValid(i) && multiB(i) instanceof BinaryBuild b && b.signal(this)) ||
                (signal(i))
                ) ? team.color : Color.white);
                if(connections[i]){
                    Draw.rect(connectionRegion, x, y, (rotation + i) % 4 * 90);
                }
                if(inputValid(i)){
                    Draw.rect(inputRegion, x, y, (rotation + i) % 4 * 90);
                }
                if(outputValid(i)){
                    Draw.rect(outputRegion, x, y, (rotation + i) % 4 * 90);
                }
            }
            Draw.color();
        }

        @Override
        public void placed(){
            super.placed();
            if(!rotInit){
                for(int i = 0; i < rotation; i++){
                    configs = IntSeq.with(
                    configs.get(3),
                    configs.get(0),
                    configs.get(1),
                    configs.get(2)
                    );
                }
                rotInit = true;
                rotation(0);
            }
            updateProximity();
        }

        @Override
        public void buildConfiguration(Table table){
            table.table(Tex.clear, t -> {
                t.table().size(40);
                addConfigButton(t, 1).align(Align.center);
                t.row();
                addConfigButton(t, 2);
                t.table().size(40);
                addConfigButton(t, 0);
                t.row();
                t.table().size(40);
                addConfigButton(t, 3).align(Align.center);
            });
        }

        public Cell<Table> addConfigButton(Table table, int index){
            return table.table(t -> {
                TextButton b = t.button(states[configs.get(index)], () -> configure(index)).size(40f).get();
                b.update(() -> b.setText(states[configs.get(index)]));
            }).size(40f);
        }

        @Override
        public Object config(){
            return configs;
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            for(int i = 0; i < 4; i++){
                configs.set(i, read.i());
            }
        }

        @Override
        public void write(Writes write){
            super.write(write);

            for(int i = 0; i < 4; i++){
                write.i(configs.get(i));
            }
        }
    }
}
