package esoterum2.world.blocks.binary;

import arc.struct.*;
import mindustry.gen.*;

public class BinaryNoBackflow extends BinaryBlock{
    public final static Queue<BinaryNoBackflowBuild> queue = new Queue<>();

    public BinaryNoBackflow(String name){
        super(name);
    }

    public class BinaryNoBackflowBuild extends BinaryBuild{
        //stealing extensively from door
        Seq<BinaryNoBackflowBuild> chained = new Seq<>();

        @Override
        public void updateSignal(){
            boolean temp = signal;
            signal = false;
            l:
            for(BinaryNoBackflowBuild b : chained){
                for(int i = 0; i < 4; i++){
                    if(!(b.multiB(i) instanceof BinaryNoBackflowBuild) &&
                    b.inputValid(i) &&
                    BinaryBlock.signal(b.multiB(i), b)
                    ){
                        signal = true;
                        break l;
                    }
                }
            }
            if(temp != signal){
                for(BinaryNoBackflowBuild b : chained){
                    b.signal = signal;
                    b.propagateSignal();
                }
            }
        }

        @Override
        public void onProximityAdded(){
            super.onProximityAdded();
            updateChained();
        }

        @Override
        public void onProximityRemoved(){
            super.onProximityRemoved();

            for(Building b : proximity){
                if(b instanceof BinaryNoBackflowBuild d){
                    d.updateChained();
                }
            }
        }

        public void updateChained(){
            chained = new Seq<>();
            queue.clear();
            queue.add(this);

            while(!queue.isEmpty()){
                var next = queue.removeLast();
                chained.add(next);

                for(var b : next.proximity){
                    if(b instanceof BinaryNoBackflowBuild d && d.chained != chained){
                        d.chained = chained;
                        queue.addFirst(d);
                    }
                }
            }
        }
    }
}
