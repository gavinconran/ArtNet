# Software Construction in Java: Recursion, Recursive Data Types, Grammars, Regex, and Parsers
This Project contains code for assignment 3 of **MIT's Software Construction in Java** [SCiJ](http://web.mit.edu/6.005/www/fa16/).
All code examples were written using Java 8, Eclipse, and Git.


The README.md is a summary of the the course readings from 14 to 18.

There are **THREE** main goals of this course:
1. **_Safe From Bugs (SFB)_**, or in other words correct
2. **_Easy To Understand (ETU)_** for other programmers, and 
3. **_Ready To Change_** in the future

### Table of Contents
14. **Recursive Problems and Recursive Data**
   * Examples of Recursive Problems: **_Fibonacci_** and **_Factorial_**
   * Code Example: package "recursion.functions"
   * A recursive function is defined in terms of **_base cases_** and **_recursive steps_**
      * In a **_base case_**, we compute the result immediately given the inputs to the function call
      * In a **_recursive step_**, we compute the result with the help of one or more recursive calls to this same function, but with the inputs somehow reduced in size or complexity, closer to a base case
   * Example of Recursive Data: Computer File System 
   * **_Reentrant code_** can be safely re-entered, meaning that it can be called again even while a call to it is underway 
   * Reentrant code keeps its state entirely in parameters and local variables, and doesn’t use static variables or global variables, and doesn’t share aliases to mutable objects with other parts of the program, or other calls to itself
   * In a **_concurrent program_**, a method may be called at the same time by different parts of the program that are running concurrently
   
15. **Recursive Data Types**
   * Example: Immutable Lists
   * Code Example: package "recursion.datatypes"
   * **_Recursive datatype definitions_**: The abstract data type **_ImList_**, and its two concrete classes **_Empty_** and **_Cons_**, form a recursive data type
   * Cons is an implementation of ImList, but it also uses ImList inside its own rep (for the rest field), so it recursively requires an implementation of ImList in order to successfully implement its contract
   * **_Functions over recursive datatypes_**: provides a convenient way to describe operations over the datatype, as functions with one case per variant
16. **Writing a Program with Abstract Data Types**
   * Writing a program (consisting of ADTs and procedures):
       * **_Choose datatypes_**. Decide which ones will be mutable and which immutable
       * **_Choose procedures_**. Write your top-level procedure and break it down into smaller steps
       * **_Spec_**. Spec out the ADTs and procedures. Keep the ADT operations simple and few at first. Only add complex operations as you need them
       * **_Test_**. Write test cases for each unit (ADT or procedure)
       * **_Implement simply first_**. Choose simple, brute-force representations
          * The point here is to put pressure on the specs and the tests, and try to pull your whole program together as soon as possible 
          * Make the whole program work correctly first. Skip the advanced features for now. Skip performance optimisation. Skip corner cases 
          * Keep a to-do list of what you have to revisit
       * **_Reimplement and iterate and optimize_**. Now that it’s all working, make it work better
17. **Regular Expressions & Grammars**
   * **_Grammars_**:
      * A grammar defines a **_set of sentences_**, where each sentence is a sequence of symbols
         * For example, our grammar for URLs will specify the set of sentences that are legal URLs in the HTTP protocol
      * The symbols in a sentence are called **_terminals (or tokens)_**
         * They’re called terminals because they are the leaves of a tree that represents the structure of the sentence 
         * They don’t have any children, and can’t be expanded any further
         * We generally write terminals in quotes, like 'http' or ':', although they can also be Regular Expressions
      * A grammar is described by a **_set of productions_**, where each production defines a nonterminal.
      * A production in a grammar has the form
         * **nonterminal ::= expression of terminals, nonterminals, and operators** 
      * Code example: grammar.parser.integer   
   * **_Regular Expressions_**:
      * A regular grammar has a special property: 
         * by substituting every nonterminal (except the root one) with its righthand side, you can reduce it down to a single production for the root, with only terminals and operators on the right-hand side   
       * Code example: package "grammar.regex"         
18. **Parser Generators**
    * A parser generator takes a grammar as input and automatically generates source code that can parse streams of characters using the grammar
    * The generated code is a **_parser_**, which takes a sequence of characters, the input, and tries to match the sequence against the grammar. 
    * The parser typically produces a **CONCRETE** **_parse tree_**, which shows how grammar productions are expanded into a sentence that matches the input. 
    * The **_root_** of the parse tree is the starting nonterminal of the grammar 
    * Each **_node_** of the parse tree expands into one production of the grammar
    * The next step is to build (or translate) an **ABSTRACT** **_Syntax Tree_** from the Concrete Parse Tree.
    * It is from the Abstract Syntax Tree (**_AST_**) that we call methods, such as, value(), differentiate(), and simplify()
    * Code Example: package "Expression.parser" [**Assignment**](http://web.mit.edu/6.005/www/fa16/psets/ps3/)
    * Beware of **_Left Recursion_**                                
      
   
