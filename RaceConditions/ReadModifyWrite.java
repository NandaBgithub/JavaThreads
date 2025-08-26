package RaceConditions;

public class ReadModifyWrite {
    
    public static class Counter {
        protected int count = 0;

        public void add(int val){
            this.count += val;
        }
        
        public int getCount(){return this.count;}
    }

    public static void main(String[] args){
        Counter counter = new Counter();

        Thread t1 = new Thread(){
            
            @Override
            public void run(){
                for (int i = 0; i < 3; i++){
                    counter.add(1);
                    System.out.println("Thread :: t1 :: count = " + counter.getCount());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t2 = new Thread(){

            @Override
            public void run(){
                for (int i = 0; i < 3; i++){
                    counter.add(1);
                    System.out.println("Thread :: t2 :: count = " + counter.getCount());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
            }
        };

        t1.start();
        t2.start();
    }
}
