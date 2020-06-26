# Software Construction in Java: Java and Abstract Data Types 
This Project contains code for assignment 3 of **Software Construction in Java** [SCiJ](http://web.mit.edu/6.005/www/fa16/).
All code examples were written using Java 8, Eclipse, and Git.


The README.md is a summary of the the course combined with comments from **_Josh Block's Effective Java_**(https://www.amazon.co.uk/Effective-Java-Second-Joshua-Bloch/dp/0321356683).

There are three big goals of this course:
1. **_Safe From Bugs (SFB)_**, or in other words correct
2. **_Easy To Understand (ETU)_** for other programmers, and 
3. **_Ready To Change_** in the future.

### Table of Contents
1. **Static Checking**
   * (SFB): Static checking helps with safety by catching type errors and other bugs before runtime.
   * (ETU): It helps with understanding, because types are explicitly stated in the code.
   * (RFC): Static checking makes it easier to change your code by identifying other places that need to change in tantem.
   * I really appreciate static checking and it is the main reason I like coding in Java.   
2. **Code Review**
   * Code review is a widely-used technique for improving software quality by human inspection.
   * Encourages good code, e.g.:
      * Don't Repeat Yourself (DRY)
      * Comments where needed
      * Fail fast
      * Avoid magic numbers
      * One purpose for each variable
      * Use good names
      * No global variables
      * Return results, don't print them
      * Use whitespace for readability
3. **Test Driven Development**
   * Test-first programming. Write tests before you write code.
   * Partitioning and boundaries for choosing test cases systematically.
   * White box testing and statement coverage for filling out a test suite.
   * Unit-testing each module in isolation as much as possible.
   * Automated regression testing to keep bugs from coming back.
4. **Specifications**
   * A specification acts as a crucial firewall between the implementor of a procedure and its client. It makes separate development possible: the client is free to write code that uses the procedure without seeing its source code, and the implementor is free to write the code that implements the procedure without knowing how it will be used.
5. **Designing Specifications**
   * Declarative specifications are the most useful in practice. Preconditions (which weaken the specification) make life harder for the client, but applied judiciously they are a vital tool in the software designer's repertoire, allowing the implementor to make necessary assumptions.
6. **Avoiding Debugging**
   * **_Avoid debugging_**. Make bugs impossible with techniques like:
      * static typing
      * automatic dynamic checking
      * immutable types and references
   * **_Keep bugs confined_**. Make the search for buggy code easier with techniques like:
      * failing fast
      * incremental development and unit testing
      * scope minimisation  
7. **Mutability and Immutability**
   * **_Mutability_** is useful for performance and convenience, but it also creates risks of bugs by requiring the code that uses the objects to be well-behaved on a global level, greatly complicating the reasoning and testing we have to do to be confident in its correctness.   
   * It is important to understand the difference between an **_immutable object_** (like a String) and an **_immutable reference_** (like a final variable).
   * A **_reference_** is a pointer to an object and an **_immutable reference_** is an arrow with a double line, indicating that the arrow can't be moved to point to a different object.
   * The key design principle here is **_Immutability_**: using immutable objects and immutable references as much as possible.
8. **Debugging**
   * Reproduce the bug as a test case and put it in your regression suite
   * Find the bug using the scientific method
   * Fix the bug thoughtfully, not slapdash
9. **Abstract Data Types**
   * Abstract data types are characterised by their operations.
   * Operations can be classified into **_creators_**, **_producers_**, **_observers_**, and **_mutators_**.
   * An ADT's specification is its set of operations and their specs.
   *  A good ADT is simple, coherent, adequate, and **_representation-independent_**.
   * An ADT is tested by generating tests for each of its operations, but using the creators, producers, mutators, and observers together in the same tests.
10. **Abstraction Types and Rep Invariants**
   * An invariant is a property that is **_always true_** of an ADT object instance for the lifetime of the object.
   * A good ADT preserves its own invariants. Invariants must be established by creators and producers and preserved by observers and mutators.
   * The **_rep invariant_** specifies legal values of the representation and should be checked at runtime with **_checkRep()_**.
   * The **_abstraction function_** maps a concrete representation to the abstract value it represents.
   * **_Representation exposure_** threatens both representation independence and invariant preservation.
11. **Interfaces**
   * Java interfaces help us formalise the idea of an abstract data type as a set of operations that must be supported by a type.
12. **Equality**
   * Equality should be an equivalence relation (reflexive, symmetric, transitive).
   * Equality and hash code must be consistent with each other, so that data structures that use hash tables (like **_HashSet_** and **_HashMap_**) work properly.
   * The abstraction function is the basis for equality in immutable data types.
   * Reference equality is the basis for equality in mutable data types; this is the only way to ensure consistency over time and avoid breaking rep invariants of hash tables.
             




