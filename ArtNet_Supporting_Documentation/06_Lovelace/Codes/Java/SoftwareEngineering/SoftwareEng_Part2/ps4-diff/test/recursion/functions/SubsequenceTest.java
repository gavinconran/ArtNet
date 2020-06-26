package recursion.functions;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import recursion.functions.Subsequence;

public class SubsequenceTest {

    @Test
    public void testfactorialIterative() {
        assertEquals("", Subsequence.subsequences(""));
        assertEquals(",a", Subsequence.subsequences("a"));
        assertEquals(",a,b,ab", Subsequence.subsequences("ab"));
        assertEquals(",a,b,ab,c,ac,bc,abc", Subsequence.subsequences("abc"));
        assertEquals("a", Subsequence.subsequences("abc").substring(1, 2));
        assertEquals("b", Subsequence.subsequences("abc").substring(3, 4));
        assertEquals("ab", Subsequence.subsequences("abc").substring(5, 7));
        assertEquals("c", Subsequence.subsequences("abc").substring(8, 9));
        assertEquals("ac", Subsequence.subsequences("abc").substring(10, 12));
        assertEquals("bc", Subsequence.subsequences("abc").substring(13, 15));
        assertEquals("abc", Subsequence.subsequences("abc").substring(16, 19));
    }
    
    @Test
    public void teststringValue() {
        assertEquals("16", Subsequence.stringValue( 16, 10 ));
        assertEquals("10000", Subsequence.stringValue( 16, 2 ));
        assertEquals("AA", Subsequence.stringValue( 170, 16 ));
    }    
    
    @Test
    public void testfullPathname() {
        assertEquals("README.md", Subsequence.fullPathname(new File("README.md")));
    }    

}
