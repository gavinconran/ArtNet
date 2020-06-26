package minesweeper.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {
    
    /**
     * Reads an input file
     * 
     * @param file: input file
     * @return a list containing board dimensions and bomb placements
     */
    static List<Integer> readFile(File file) {

        BufferedReader br = null;
        FileReader fr = null;
        
        List<Integer> params = new ArrayList<Integer>();

        try {

            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String sCurrentLine;

            br = new BufferedReader(new FileReader(file));
            
            sCurrentLine = br.readLine();
            String[] tokens = sCurrentLine.split(" ");
            params.add(Integer.valueOf(tokens[0]));
            params.add(Integer.valueOf(tokens[1]));
            
            int bombPosition = 0;
            while ((sCurrentLine = br.readLine()) != null) {
                String[] bombTokens = sCurrentLine.split(" ");
                for (String token: bombTokens) {
                    if (token.equals("1")) {
                        params.add(bombPosition); 
                    }
                    bombPosition++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return params;
    } // end method
    
    /**
     * Generate a random number within a range of numbers
     * 
     * @param min: minimum of output range
     * @param max: maximum of output range
     * @return int random number
     */
     static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
     
     /**
      * Randomly generates bombs used to initialise board
      * 
      * @param size dimension of square grid
      * @param debug debug flag
      * @return a list containing board dimensions and (randomly generated) bomb placements
      */
     static List<Integer> generateBombs( int size, boolean debug ) {
         
         int squares = size * size; // number of squares
         int randBombNum;           // random number variable
         List<Integer> params = new ArrayList<Integer>();  // list of bomb positions
         
         if ( debug ) {
             params.add(3);
         } else {
             for ( int i = 0; i < squares; i++ ) {
                 // Add a bomb position to params ( 1 in 4 chance)
                 randBombNum = Util.getRandomNumberInRange(1, 4);
                 if (randBombNum == 1)
                     params.add(i);
             }    
         } 
         return params;
     }
    

}
