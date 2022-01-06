package esoterum2.world.blocks.binary.transmission;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.io.*;
import esoterum2.*;
import esoterum2.world.blocks.binary.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;

import static mindustry.Vars.*;

public class BinaryNode extends BinaryBlock{

    public int range;

    public TextureRegion centerRegion;

    public BinaryNode(String name){
        super(name);
        outputs = new boolean[]{true, true, true, true};
        inputs = new boolean[]{true, true, true, true};
        range = 6;
        rotate = false;
        rotateHighlight = false;
        configurable = true;

        config(Point2.class, (BinaryNodeBuild tile, Point2 i) -> {
            tile.disconnect();
            tile.config = i;
            BinaryNodeBuild link = tile.linkedNode();
            if(link == null){
                tile.config = new Point2(0, 0);
            }else{
                link.disconnect();
                link.config = new Point2(-i.x, -i.y);
                link.updateProximity();
            }
            tile.updateProximity();
        });

        configClear(BinaryNodeBuild::disconnect);
    }

    @Override
    public void load(){
        super.load();
        centerRegion = Core.atlas.find(name + "-center");
    }

    @Override
    protected TextureRegion[] icons(){
        return new TextureRegion[]{
        baseRegion, highlightRegion, centerRegion
        };
    }

    @Override
    public void init(){
        super.init();
        clipSize = Math.max(clipSize, (range * tilesize + 8f) * 2f);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        Drawf.circles(x * tilesize + offset, y * tilesize + offset, range * tilesize, Color.white);
        Draw.reset();
    }

    public boolean linkValid(Tile tile, Tile other){
        if(other == null || tile == null || other.dst(tile) > range * tilesize) return false;

        return other.block() == tile.block() && tile.team() == other.team();
    }

    public class BinaryNodeBuild extends BinaryBuild{
        public Point2 config = new Point2(0, 0);
        public boolean inputSignal;

        public BinaryNodeBuild linkedNode(){
            return (world.build(tileX() + config.x, tileY() + config.y) instanceof BinaryNodeBuild b && b != this) ? b : null;
        }

        @Override
        public void updateSignal(){
            inputSignal = false;
            for(int i = 0; i < 4; i++){
                if(inputValid(i)){
                    inputSignal |=BinaryBlock.signal(multiB(i),this);
                }
            }
            BinaryNodeBuild link = linkedNode();
            if(link == null){
                signal = false;
            }else if(link.signal != inputSignal){
                link.signal = inputSignal;
                link.propagateSignal();
            }
        }

        @Override
        public void draw(){
            super.draw();
            BinaryNodeBuild c = linkedNode();
            Color lineColor = inputSignal || (c != null && c.inputSignal) ? team.color : Color.white;
            Draw.color(lineColor);
            Draw.rect(centerRegion, x, y, rotateHighlight ? rotdeg() : 0);
            Draw.z(Layer.power);
            if(c != null){
                Lines.stroke(1f, lineColor);
                Lines.line(
                x, y,
                c.x, c.y,
                false
                );
            }
            Draw.reset();
        }

        @Override
        public void drawConfigure(){
            Tmp.c1.set(inputSignal ? team.color : Color.white);

            Drawf.circles(x, y, size * tilesize / 2f + 1f + Mathf.absin(Time.time, 4f, 1f), Tmp.c1);
            Drawf.circles(x, y, range * tilesize, Tmp.c1);

            BinaryNodeBuild connection = linkedNode();
            if(connection != null){
                Drawf.square(connection.x, connection.y, connection.block.size * tilesize / 2f + 1f, Tmp.c1);
            }
        }

        @Override
        public boolean onConfigureTileTapped(Building other){
            if(linkValid(tile, other.tile)){
                if(this == other || other == linkedNode()){
                    disconnect();
                }else{
                    configure(new Point2(other.tileX() - tileX(), other.tileY() - tileY()));
                }
                return false;
            }
            return true;
        }

        @Override
        public void placed(){
            super.placed();

            BinaryNodeBuild c = linkedNode();
            if(c != null && c.linkedNode() != this){
                c.configure(this);
            }
        }

        @Override
        public void remove(){
            super.remove();
            disconnect();
        }

        public void disconnect(){
            BinaryNodeBuild link = linkedNode();
            config = new Point2(0, 0);
            updateProximity();
            if(link != null){
                link.disconnect();
            }
        }

        @Override
        public Object config(){
            return config;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.i(config.x);
            write.i(config.y);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            config = new Point2(read.i(), read.i());
        }
    }
}