package RaceConditions;

public class ThreadLock extends Thread {
    String name;
    SharedResource sharedObject;
    private boolean isRunning = false;

    public ThreadLock(String name, SharedResource r){
        this.name = name;
        this.sharedObject = r;
    }

    public synchronized void doStop(){
        this.isRunning = false;
    }

    private synchronized boolean isRunning(){
        return this.isRunning == false;
    }

    @Override
    public void run(){
        isRunning = true;
        while(isRunning()){
            sharedObject.test(name);
        }
    }


    public static class SharedResource{
        public synchronized void test(String name){
            
            for (int i = 0; i < 10; i++){
                System.out.println(name + " :: " + "iteration " + i);

                try {
                    Thread.sleep(500);
                } catch (Exception e){
                    System.out.print(e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args){
        SharedResource obj = new SharedResource();
        (new ThreadLock("THREAD 1", obj)).start();
        (new ThreadLock("THREAD 2", obj)).start();
        (new ThreadLock("THREAD 3", obj)).start();
    }
}
