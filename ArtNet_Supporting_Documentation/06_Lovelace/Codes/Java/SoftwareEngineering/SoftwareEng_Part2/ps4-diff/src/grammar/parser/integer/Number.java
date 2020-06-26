package grammar.parser.integer;

public class Number implements IntegerExpression {

        private final int n;
        
     // Lazily initialized, cached hashCode
        private volatile int hashCode; 
        
        // Abstraction function
        //    represents the integer n
        // Rep invariant
        //    true
        // Safety from rep exposure
        //    all fields are immutable and final
        
        /** Make a Number. */
        public Number(int n) {
            this.n = n;
        }
        
        @Override 
        public int value() {
            return n;
        }
        
        @Override 
        public String toString() {
            return String.valueOf(n);
        }
        
        @Override
        public boolean equals(Object that) {
            if (!(that instanceof Number)) return false;
            Number thatNumber = (Number) that;
            return (this.n == thatNumber.n);
        }
        
        @Override 
        public int hashCode() {
            int result = hashCode;
            if (result == 0) {
                result = 17;
                result = 31 * result + n;
                hashCode = result;
            }
            return result;
       }
        
    }
