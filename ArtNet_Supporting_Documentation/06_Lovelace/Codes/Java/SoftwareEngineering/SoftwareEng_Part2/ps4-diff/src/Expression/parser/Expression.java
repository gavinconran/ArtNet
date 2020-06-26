package Expression.parser;

import java.util.Map;

/** Immutable type representing a mathematical expression. */
public interface Expression {
    // Data type definition
    //    Expression = Term(t:String)
    //                        + Plus(left:Expression, right:Expression)
    //                        + Times(left:Expression, right:Expression)
    
    /** @return the ABSTRACT version of this CONCRETE expression */
    public Expression parse();
    
    /** @return the expression differentiated w.r.t a variable */
    public Expression differentiate(String wrtVar);
    
    /** @return the simplified expression */
    public Expression simplify(Map<String, Double> vars);
    

}
