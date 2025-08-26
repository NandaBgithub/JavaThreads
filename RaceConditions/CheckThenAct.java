package RaceConditions;

import java.util.HashMap;
import java.util.Map;

public class CheckThenAct {
    
    // Expected behaviour
    // The first thread should insert key value pair
    // Second thread should remove the key value pair but it doesnt happen, due to the race condition
    public static void checkThenAct(Map<String, String> sharedMap){
        if (sharedMap.containsKey("key")){
            String val = sharedMap.remove("key");
            if (val == null){
                System.out.println("Value for 'key' was null");
            } 
        }else {
            sharedMap.put("key", "value");
            System.out.println(sharedMap.toString());
        }
    }

    public static void main(String[] args){
        Map<String, String> sharedMap = new HashMap<>();

        Thread t1 = new Thread(){
            @Override
            public void run(){
                
                checkThenAct(sharedMap);
                System.out.println("Thread :: t1");
            }
        };

        Thread t2 = new Thread(){
            @Override
            public void run(){
                
                checkThenAct(sharedMap);
                System.out.println("Thread :: t2");
            }
        };

        t1.start();
        t2.start();
    }
}
