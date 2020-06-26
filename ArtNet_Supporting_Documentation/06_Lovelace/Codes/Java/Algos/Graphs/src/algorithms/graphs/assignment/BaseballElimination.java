package algorithms.graphs.assignment;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {
    
    private ST<String, Integer> st; // string -> team integer array
    private String[] keys;      // index -> team
    private ST<String, ST<String, Integer>> teams; // team -> team details
    private int numberOfTeams; // number of teams
    private List<String> listOfTeams;  // bag of teams
    private ST<String, SET<String>> eliminated; // team -> eliminating teams

    
    public BaseballElimination(String filename) {
        
        if (filename == null)
            throw new NullPointerException("filename is null");
        
        st = new ST<String, Integer>();
        
        // build st (string -> indices)
        // and keys (index -> team
        In in = new In(filename);
        numberOfTeams = in.readInt();  // read in number of teams
        teams = new ST<String, ST<String, Integer>>(); // ST of teams and team details
        keys = new String[numberOfTeams]; // index -> team name
        listOfTeams = new ArrayList<String>();
        eliminated = new ST<String, SET<String>>(); 

        in.readLine(); // read the rest of the line
        int keysIndex = 0;  // set index to 0
        while (!in.isEmpty()) {
            String[] a = in.readLine().trim().split("[ ]+"); // read in a teams details
            String teamName = a[0]; // capture team name
            listOfTeams.add(teamName); // add team name to bag of teams
            keys[keysIndex] = teamName;  // add team name to index
            
            // ST of details of each team "got" by teamName
            ST<String, Integer> teamDetails = new ST<String, Integer>();
            // put team index, wins, losses, remaining into teamDeatils
            teamDetails.put("index", keysIndex);
            teamDetails.put("wins", Integer.parseInt(a[1]));
            teamDetails.put("losses", Integer.parseInt(a[2]));
            teamDetails.put("remaining", Integer.parseInt(a[3]));
            // set eliminated to 0 (false)
            teamDetails.put("eliminated", 0);
            
            // put number of games to play other teams into teamDeatils
            for (int i = 4; i < a.length; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(i - 4);
                String strI = sb.toString();
                
                teamDetails.put(strI, Integer.parseInt(a[i]));
            }
            // put team details into teams
            teams.put(teamName, teamDetails);
            // add team name and index into st
            st.put(teamName, keysIndex++);
        } 
        
        for (String teamX : listOfTeams) {
            // Create a set to capture teams which eliminate teamX 
            SET<String> eliminatingTeamsX = new SET<String>();
            // create a list of teams that does not contain teamX
            List<String> reducedList = new ArrayList<String>(listOfTeams);
            reducedList.remove(listOfTeams.indexOf(teamX));
            // Calculate the total number of games to be played
            int gamesToPlay = numGames(numberOfTeams()-2);
            // Compute the number of vertices in the FlowNetwork
            int V = 1 + gamesToPlay + (numberOfTeams() - 1) + 1;
            // set s and t
            int s = 0, t = V-1;
            // Create flowNetwork
            FlowNetwork G = new FlowNetwork(V);
            // index1 is used to compute games
            int index1 = 0;
            // gameCount tracks the vertex of each game
            int gameCount = 1;
            // Elimination
            for (String teamI: reducedList) {  
                index1++;
                if (wins(teamX) + remaining(teamX) < wins(teamI)) { // Trivial elimination
                    teams.get(teamX).put("eliminated", 1);
                    eliminatingTeamsX.add(teamI);
                    break;
                }  else { // Non-trivial elimination
                    for (int index2 = index1; index2 < reducedList.size(); ++index2) {
                            // add edges
                            double capacity = against(teamI, reducedList.get(index2));
                            // add s to game vertices
                            G.addEdge(new FlowEdge(s, gameCount, capacity));
                            G.addEdge(new FlowEdge(gameCount, reducedList.indexOf(teamI) + gamesToPlay + 1, Double.POSITIVE_INFINITY));    
                            G.addEdge(new FlowEdge(gameCount++, index2 + gamesToPlay + 1, Double.POSITIVE_INFINITY));    
                    } // close index2 loop
                        // add edges between teams and t
                        double capacity = wins(teamX) + remaining(teamX) - wins(keys[index1 - 1]);
                        G.addEdge(new FlowEdge(index1 + gamesToPlay, t, capacity));      
                    } // close ifelse  
                } // close teamI loop    
            
            
                // compute maximum flow 
                FordFulkerson maxflow = new FordFulkerson(G, s, t);
                
                // Identify those teams already eliminated
                if (teams.get(teamX).get("eliminated") == 0) {
                    for (int i = gamesToPlay + 1; i < gamesToPlay + 1 + reducedList.size(); i++) {
                        if (maxflow.inCut(i)) {
                            eliminatingTeamsX.add(reducedList.get(i - (gamesToPlay+1)));
                        }
                        if (eliminatingTeamsX.size() > 0) {
                            teams.get(teamX).put("eliminated", 1); 
                        }
                    }
                }
            
            eliminated.put(teamX, eliminatingTeamsX);
        } // close teamX  loop
    } // close method
    
    // Helper Function
    private static int numGames(int n) {
        int fact = 0; // this  will be the result
        for (int i = 1; i <= n; i++) {
            fact += i;
        }
        return fact;
    }
        
    
    // return number of teams
    public int numberOfTeams() {
        
        return numberOfTeams;
    }
    
    // return teams as an Iterable
    public Iterable<String> teams() {
        
        return listOfTeams;
    }
    
    // return the number of wins for team
    public int wins(String team) {
        
        if (!listOfTeams.contains(team))
            throw new java.lang.IllegalArgumentException("not a valid team");
     
        return teams.get(team).get("wins");
    }
    
    // return the number of losses for team
    public int losses(String team) {
        
        if (!listOfTeams.contains(team))
            throw new java.lang.IllegalArgumentException("not a valid team");
        
        return teams.get(team).get("losses");
    }
    
    // return the number of remaining games for team
    public int remaining(String team) {
        
        if (!listOfTeams.contains(team))
            throw new java.lang.IllegalArgumentException("not a valid team");
        
        return teams.get(team).get("remaining");
    }
    
    // return the number of games team1 has to play team2
    public int against(String team1, String team2) {
        
        if (!listOfTeams.contains(team1) || !listOfTeams.contains(team2))
            throw new java.lang.IllegalArgumentException("not a valid team");
        
        // Convert team2 to index then to a string
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(st.get(team2));
        String strI = sb.toString();
        
        return teams.get(team1).get(strI);
    }
    
    // return true/false whether team has been eliminated already
    public boolean isEliminated(String team) {
        
        if (!listOfTeams.contains(team))
            throw new java.lang.IllegalArgumentException("not a valid team");
        
        return teams.get(team).get("eliminated") == 1;
    }
    
    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        
        if (!listOfTeams.contains(team))
            throw new java.lang.IllegalArgumentException("not a valid team");
        
        if (eliminated.get(team).isEmpty())
            return null;
        else
            return eliminated.get(team);
    }
    
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
