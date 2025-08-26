public class MyThread1 extends Thread{
    public MyThread1(){}

    @Override
    public void run(){
        System.out.println("thread ran");
    }

    public static void main(String[] args){
        MyThread1 mythread = new MyThread1();
        mythread.start();
    }
}