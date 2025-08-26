package ThreadSafety;

// You can use immutable values to 
// create thread safety on a class
// HOWEVER, the USE of this immutable value
// is not necessarily thread safe.
public class Immutability {
    public class ImmutableValue{
        private int value = 0;

        public ImmutableValue(int value){
            this.value = value;
        }

        public int getValue(){
            return this.value;
        }

        public ImmutableValue add(int value){
            return new ImmutableValue(this.value + value);
        }
    }
}
