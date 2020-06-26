package expressivo;

import java.util.Map;


public class Term implements Expression {
    
    // Lazily initialized, cached hashCode
    private volatile int hashCode;     
    
    private final String t;
    
    // Abstraction function
    //    represents the String t
    // Rep invariant
    //    true
    // Safety from rep exposure
    //    all fields are immutable and final
    
    /** Make a Term Expression. */
    public Term(String t) {
        this.t = t;
        
        RepCheck();
    }
    
    // RepCheck Function
    private void RepCheck() {        
        assert t.length() > 0; 
    }
    
    /**
     * Add two expressions.
     * @param left expression
     * @param right expression
     * @return expression
     */
    @Override
    public Expression plus(Expression left, Expression right) {
        RepCheck();
        return new Plus(left, right);
    }

    /**
     * Multiply two expressions.
     * @param left expression
     * @param right expression
     * @return expression
     */
    @Override
    public Expression times(Expression left, Expression right) {
        RepCheck();
        return new Times(left, right);
    }
    
    /**
     * @return human-readable representation of this Expression
     */    
    @Override 
    public String toString() {
        return String.valueOf(t);
    }
    
    @Override
    public boolean equals(Object that) {
        if (!(that instanceof Term)) return false;
        Term thatTerm = (Term) that;
        return (this.t.equals(thatTerm.t));
    }
    
    @Override 
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = 17;
            result = 31 * result + this.t.hashCode();
            hashCode = result;
        }
        return result;
   }
    
    /**
     * @return a Differentiated Abstract Syntax Tree version of this Concrete parsed Tree
     */
    @Override
    public Expression differentiate(String wrtVar) {
        
        if (this.t.equals(wrtVar)) // it term is equals to w.r.t. variable
            return new Term("1");
        else
            return new Term("0"); // term is a non w.r.t. variable and therefore equal to 0
        
    }
    
    /** @return the simplified expression as a String */
    public Expression simplify(Map<String, Double> environment) {
       
        if (environment.keySet().contains(t)) 
            return new Term(environment.get(t).toString());
        else
            return new Term(this.toString());
    }

}
