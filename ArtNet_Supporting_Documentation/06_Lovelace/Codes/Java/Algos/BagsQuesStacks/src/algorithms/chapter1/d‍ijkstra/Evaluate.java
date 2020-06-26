package algorithms.chapter1.d‍ijkstra;

import java.util.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Evaluate {

    public static void main(String[] args) {
        
        In in = new In(args[0]);
        Stack<String> ops = new Stack<String>();
        Stack<Double> vals = new Stack<Double>();
        
        //while (!StdIn.isEmpty()) {
        while (!in.isEmpty()) {    
            //String s = StdIn.readString();
            String s = in.readString();
            //StdOut.println("s  " + s);

            if      (s.equals("("))               ;
            else if (s.equals("+"))    ops.push(s);
            else if (s.equals("-"))    ops.push(s);
            else if (s.equals("*"))    ops.push(s);
            else if (s.equals("/"))    ops.push(s);
            else if (s.equals("sqrt")) ops.push(s);
            else if (s.equals(")")) { //Pop, evaluate, and push result if token is ")"
                String op = ops.pop();
                double v = vals.pop();
                if      (op.equals("+"))    v = vals.pop() + v;
                else if (op.equals("-"))    v = vals.pop() - v;
                else if (op.equals("*"))    v = vals.pop() * v;
                else if (op.equals("/"))    v = vals.pop() / v;
                else if (op.equals("sqrt")) v = Math.sqrt(v);
                vals.push(v);
            } // Token not operator or paren: push double value
            else vals.push(Double.parseDouble(s));            
        }
        StdOut.println(vals.pop());    

    }

}
