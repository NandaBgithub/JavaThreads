public class MyThread2{
    public MyThread2(){}

    public static void main(String[] args){
        System.out.println(Thread.currentThread());

        Thread myThread = new Thread(new Runnable(){
            @Override
            public void run(){
                System.out.println(Thread.currentThread());
                System.out.println("Ran thread");
            }
        });

        myThread.start();
    }
}