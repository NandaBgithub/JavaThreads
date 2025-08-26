package RaceConditions;

// A way to handle multiple critical sections in one function
// is to synchronize the certain blocks of code
public class CriticalBlocks {
    public class TwoSums {
        private int sum1 = 0;
        private int sum2 = 0;

        // This shouldnt be done. Since small integers are reused and shared accross the JVM,
        // This integer lock object will then not guaranteed to be unique for this program
        // thus, you could be synchronizing with locks on a different program. 
        private Integer sum1Lock = 1;
        private Integer sum2Lock = 2;

        // The alternative lock to guarantee uniqueness for this program is
        @SuppressWarnings("unused")
        private final Object lock1 = new Object();
        @SuppressWarnings("unused")
        private final Object lock2 = new Object();
        
        public void add(int val1, int val2){
            synchronized(this.sum1Lock){ // Unsafe
                this.sum1 += val1;
            }

            synchronized(this.lock2){ // Safe
                this.sum2 += val2;
            }
        }

        public int getSum1(){return this.sum1;}
        public int getSum2(){return this.sum2;}
    }


    public static void main(String[] args) throws InterruptedException{
    
        TwoSums t = (new CriticalBlocks()).new TwoSums();

        // Imma try a lambda way of making a thread
        // This lambda is passed as a Runnable
        Thread t1 = new Thread(()->{
            for (int i = 0; i<10; i++){
                t.add(1, 2);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++){
                t.add(3, 4);
            }
        });

        t1.start();
        t2.start();

        // Wait for both threads to finish b4 print
        t1.join();
        t2.join();

        System.out.println(t.getSum1());
        System.out.println(t.getSum2());
    }
}

// The flow of this program
/*
t1 starts, locks the first critical region and adds 1 to sum1 ten times
t2 starts, checks first critical region but t1 has it locked, so
it moves to critical region 2 and locks it, adding 4 to sum2 ten times.
t1 finishes, releases the lock, t2 finishes, releases the lock
t1 now locks second critical region and adds 2 to sum2 ten times.
t2 now locks the first critical region and adds 3 to sum1 ten times.
.join is called for both threads, signifying to the main thread that the
rest of the process instructions can only continue after t1 and t2 threads finish.
t1 and t2 now finish, and print statements executed. Main thread finishes. 
*/

// This version is safe, two different add operations can be done on different threads
// and the result is two sepparate sums of numbers. The thread using the first lock that 
// increments sum1 does not interfere or edit sum2 everytime it calls the add method.
