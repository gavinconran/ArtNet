package Expression.parser;

import java.util.Map;

public class PlusExp implements Expression {
    
        
    private final Expression left;

    private final Expression right;
        
    // Lazily initialized, cached hashCode
    private volatile int hashCode; 
        
    // Abstraction function
    //    represents the sum of two expressions left+right
    // Rep invariant
    //    true
    // Safety from rep exposure
    //    all fields are immutable and final
        
    /** Make a PlusExp which is the sum of left and right. */
    public PlusExp(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        
        RepCheck();
    }    
    
    // RepCheck Function
    private void RepCheck() {        
        assert left.toString().length() > 0; 
        assert right.toString().length() > 0; 
    }
    
    /** @return the ABSTRACT version of this CONCRETE expression */
    @Override
    public Expression parse() {
        return new PlusExp(left.parse(), right.parse());    
    }
    
    /** @return the expression differentiated w.r.t a variable */
    @Override
    public Expression differentiate(String wrtVar) {
        return new PlusExp(left.differentiate(wrtVar), right.differentiate(wrtVar));
    }
    
    /** @return the simplified expression */
    @Override
    public Expression simplify(Map<String, Double> vars) {
        return new PlusExp(left.simplify(vars), right.simplify(vars));    
    }
    
    /**
     * @return human-readable representation of this Expression
     */   
    @Override 
    public String toString() {
        return left + "+" + right;
//        return "(" + left + ")+(" + right + ")";
    }
    
    @Override
    public boolean equals(Object that) {
        if (!(that instanceof PlusExp)) return false;
        PlusExp thatPlusExp = (PlusExp) that;
        return this.left.equals(thatPlusExp.left) &&
                this.right.equals(thatPlusExp.right) &&
                        this.left.hashCode() == thatPlusExp.left.hashCode() &&
                        this.right.hashCode() == thatPlusExp.right.hashCode();
    }
    
    @Override 
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = 31 * result + this.left.hashCode() - this.right.hashCode();
            hashCode = result;
        }
        return result;
   }
}
