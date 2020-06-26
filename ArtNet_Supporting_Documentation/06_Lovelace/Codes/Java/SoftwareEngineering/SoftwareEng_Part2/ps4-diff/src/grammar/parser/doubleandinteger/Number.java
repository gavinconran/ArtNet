package grammar.parser.doubleandinteger;

public class Number implements IntegerExpression {

        private final double n;
        
     // Lazily initialized, cached hashCode
        private volatile int hashCode; 
        
        // Abstraction function
        //    represents the integer n
        // Rep invariant
        //    true
        // Safety from rep exposure
        //    all fields are immutable and final
        
        /** Make a Number. */
        public Number(double n) {
            this.n = n;
        }
        
        @Override 
        public double value() {
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
                result = 31 * result + (int) n;
                hashCode = result;
            }
            return result;
       }
        
    }
