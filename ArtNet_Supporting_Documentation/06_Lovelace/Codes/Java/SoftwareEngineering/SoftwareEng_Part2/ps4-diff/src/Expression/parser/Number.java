package Expression.parser;

import java.util.Map;

public class Number implements Expression {

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

        @Override
        public Expression parse() {
            return new Number(this.n);
        }

        @Override
        public Expression differentiate(String wrtVar) {
            return new Number(0);
        }

        /**
         * @return a Simplified Abstract Syntax Tree version of this Concrete parsed Tree
         */
        @Override
        public Expression simplify(Map<String, Double> vars){
            return new Number(this.n);
        }
        
    }
