package ThreadSafety;

public class SharedResources{

    @SuppressWarnings("unused")
    public long generateInt(){
        // Local variables like this are threadsafe
        long threadSafeInt = 0;

        threadSafeInt++;

        // Local objects like this are also thread safe
        Object threadSafeLocalObject = new Object();
        
        // In common with local object and variable is that 
        // they abide by the thread control and escape rule
        return threadSafeInt;
    }

    // This is unsafe.
    // Two threads calling this instance will lead to a race condition
    // It does not abide by the thread control and escape rule
    public class unsafeStringBuilder{
        StringBuilder builder = new StringBuilder();

        public void add(String text){
            this.builder.append(text);
        }
    }
}