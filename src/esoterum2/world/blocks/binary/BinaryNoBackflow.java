package esoterum2.world.blocks.binary;

public class BinaryNoBackflow extends BinaryBlock{
    public BinaryNoBackflow(String name){
        super(name);
    }

    public class BinaryNoBackflowBuild extends BinaryBuild{

        public boolean shouldAccept = true;

        @Override
        public void updateSignal(){
            if(shouldAccept){
                super.updateSignal();
            }
        }

        @Override
        public void propagateSignal(){
            shouldAccept = false;
            super.propagateSignal();
            shouldAccept = true;
        }
    }
}
