package msc.distributed.sockets.client;

public class Timings {

    private double timeSetup;
    private double timeTotal;
    private double timeStartSending;
    private double timeEnd;
    
    Timings() {
        this.timeSetup = 0.0;
        this.timeSetup = 0.0;
        this.timeTotal = 0.0;
        this.timeStartSending = 0.0;
        this.timeEnd = 0.0;
    }


    // set starting time
    public void recordTimeStartSetUp() {
        this.timeSetup = System.currentTimeMillis();
    } 
    
    // get starting time
    public double getTimeStartSetUp() {
        return this.timeSetup;
    } 

    // set Set up time
    public void recordSetUpTime() {
        this.timeSetup = System.currentTimeMillis() - getTimeStartSetUp();
    } 
    
    // get set up time
    public double getSetUpTime() {
        return this.timeSetup;
    }

   // Record set up starting time
    public void recordTimeStartSending()
    {
        this.timeStartSending = System.currentTimeMillis();
    } // end method recordTimeStartSending

    // Record set up starting time
    public void recordTimeEnd()
    {
        this.timeEnd = System.currentTimeMillis();
    } // end method recordTimeStartSending

    // Record total time
    public void recordTimeTotal()
    {
        timeTotal = getTimeTotal() + getTimeTaken();
    } // end method recordTimeStartSending

    // Return time taken to send and receive data
    public double getTimeStart()
    {
        return this.timeStartSending;
    } // end method getTimestart

    // Return end time
    public double getTimeEnd()
    {
        return this.timeEnd;
    } // end method getTimestart

    // Return start time taken for sending data
    public double getTimeTaken()
    {
        return getTimeEnd() - getTimeStart();
    } // end method getTimeTaken

    // Return start time taken for sending data
    public double getTimeTotal()
    {
        return this.timeTotal;
    } // end method getTimeTaken


}
