public class StoppingThread {
    public static void main(String[] args) throws InterruptedException{
        // This thread is supposed to run for four iterations 1 second apart
        // var is needed here to allow invoking methods of anonymous classes
        var t = new Thread(){
            private boolean run = true;
            private int targetIteration = 4;
            private int currentIteration = 0;

            private synchronized boolean isRunning(){
                return this.run == true;
            }

            public synchronized void stopThread(){
                System.out.println("Thread stopped abruptly");
                this.run = false;
            }

            @Override
            public void run(){
                while(isRunning()){
                    if (currentIteration <= targetIteration){
                        System.out.println("Thread running");
                        currentIteration += 1;
                        
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e){System.out.println(e.toString());};
                    }
                }
            }
        };

        t.start();
        // This stops the thread before the last iteration can be made
        Thread.sleep(3000);
        t.stopThread(); 
    }
}
