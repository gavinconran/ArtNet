package expressivo;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Times implements Expression {
    
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
    public Times(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        
        RepCheck();
    }
    
    // RepCheck Function
    private void RepCheck() {        
        assert left.toString().length() > 0; 
        assert right.toString().length() > 0; 
    }

    /**
     * Add two expressions.
     * @param left expression
     * @param right expression
     * @return IllegalArgumentException if the expression is invalid
     */
    @Override
    public Expression plus(Expression left, Expression right) {
        throw new UnsupportedOperationException();
    }
        
    /**
     * Multiply two expressions.
     * @param left expression
     * @param right expression
     * @return expression
     */
    @Override
    public Expression times(Expression let, Expression right) {
        
        return new Times(left, right);
    }
    
    /**
     * @return human-readable representation of this Expression
     */   
    @Override
    public String toString() {
//        return left + "*(" + right + ")";
        return left + "*" + right;
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof Times))
            return false;
        Times thatPlus = (Times) that;
        return this.left.equals(thatPlus.left) && this.right.equals(thatPlus.right)
                && this.left.hashCode() == thatPlus.left.hashCode()
                && this.right.hashCode() == thatPlus.right.hashCode();
    }

    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = 31 * result + (this.left.hashCode() * this.right.hashCode());
            hashCode = result;
        }
        return result;
    }
    
    /** @return the expression differentiated w.r.t a variable */
    @Override
    public Expression differentiate(String wrtVar) {
        Expression LHS = new Times(left, right.differentiate(wrtVar));
        // PROBLEM: Not multiplying right with more than 1 term returned from left.differentite(wrtVar) 
        // PROBLEM: Fixed -> problem was not putting terms in parenthesis 
        Expression RHS = new Times(right, left.differentiate(wrtVar));
        return new Plus(LHS, RHS);
    }
    
    /** @return the simplified expression */
    @Override
    public Expression simplify(Map<String, Double> environment) {
        Expression returnValue =  new Times(left.simplify(environment), right.simplify(environment));
        
        // if expression matches a times operation with doubles regex then evaluate expression
        Pattern regex = Pattern.compile("([0-9]*\\.?[0-9]+([0-9]+)?)([+*])([0-9]*\\.?[0-9]+([0-9]+)?)");
        Matcher m = regex.matcher(returnValue.toString());
        if (m.matches()) {
                double evalPlus = Double.parseDouble(m.group(1)) * Double.parseDouble(m.group(4));
                returnValue = new Term(String.valueOf(evalPlus));
        }
        
        return returnValue;
    }

}
