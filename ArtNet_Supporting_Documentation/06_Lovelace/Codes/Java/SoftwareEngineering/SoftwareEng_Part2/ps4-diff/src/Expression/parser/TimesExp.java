package Expression.parser;

import java.util.Map;

public class TimesExp implements Expression {

    private final Expression left, right;

    // Lazily initialized, cached hashCode
    private volatile int hashCode;

    // Abstraction function
    // represents the sum of two expressions left+right
    // Rep invariant
    // true
    // Safety from rep exposure
    // all fields are immutable and final

    /** Make a Plus which is the sum of left and right. */
    public TimesExp(Expression left, Expression right) {
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
        return new TimesExp(left.parse(), right.parse());
    }

    /** @return the expression differentiated w.r.t a variable */
    @Override
    public Expression differentiate(String wrtVar) {
        Expression LHS = new TimesExp(left, right.differentiate(wrtVar));
        // PROBLEM: Not multiplying right with more than 1 term returned from left.differentite(wrtVar) 
        // PROBLEM: Fixed -> problem was not putting terms in parenthesis 
        Expression RHS = new TimesExp(right, left.differentiate(wrtVar));
        return new PlusExp(LHS, RHS);
    }
    

    /** @return the simplified expression */
    @Override
    public Expression simplify(Map<String, Double> vars) {
        return new TimesExp(left.simplify(vars), right.simplify(vars));
    }

    /**
     * @return human-readable representation of this Expression
     */   
    @Override
    public String toString() {
        return left + "*(" + right + ")";
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof TimesExp))
            return false;
        TimesExp thatPlus = (TimesExp) that;
        return this.left.equals(thatPlus.left) && this.right.equals(thatPlus.right)
                && this.left.hashCode() == thatPlus.left.hashCode()
                && this.right.hashCode() == thatPlus.right.hashCode();
    }

    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = 31 * (result + this.left.hashCode() * this.right.hashCode());
            hashCode = result;
        }
        return result;
    }
}
