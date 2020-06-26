package minesweeper;

public class Square {
    
    
    //rep
    private String state;
    private boolean bombState;
    
    // regex for Square state
    String regex = "F|-|[0-8]| ";
    
    
    //constructor
    public Square(String state, boolean bombState) {
        this.state = state;
        this.bombState = bombState;
    }
    
    // RepCheck Function
    private void RepCheck() {        
        assert this.state.matches(regex); 
        assert this.bombState == true || this.bombState == false;
    }
    
    public void changeState(String state) {
        this.state = state;
        RepCheck();
    }
    
    public String getState() {
        return this.state;
    }
    
    public boolean hasBomb() {
        return this.bombState;
    }
    
    public void setBomb(boolean bombState) {
        this.bombState = bombState;
    }
}
