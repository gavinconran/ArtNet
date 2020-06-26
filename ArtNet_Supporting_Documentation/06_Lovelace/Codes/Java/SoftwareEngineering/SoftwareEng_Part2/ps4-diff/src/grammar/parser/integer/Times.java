package grammar.parser.integer;

class Times implements IntegerExpression {
    private final IntegerExpression left, right;
    
 // Lazily initialized, cached hashCode
    private volatile int hashCode; 
     
     // Abstraction function
     //    represents the product of two expressions left+right
     // Rep invariant
     //    true
     // Safety from rep exposure
     //    all fields are immutable and final
     
     /** Make a Times which is the product of left and right. */
     public Times(IntegerExpression left, IntegerExpression right) {
        this.left = left;
         this.right = right;
     }
     
     @Override 
     public int value() {
        return left.value() * right.value();
     }
     
     @Override 
     public String toString() {
         return "(" + left + ")*(" + right + ")";
     }
     
     @Override
     public boolean equals(Object that) {
         if (!(that instanceof Times)) return false;
         Times thatPlus = (Times) that;
         return this.left.value() * this.right.value() == thatPlus.left.value() * thatPlus.right.value();
     }
     
     @Override 
     public int hashCode() {
         int result = hashCode;
         if (result == 0) {
             result = 31 * result + this.left.value() * this.right.value();
             hashCode = result;
         }
         return result;
    }
 }
