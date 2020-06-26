package echoclient;

public class Timings {

    private double timeSetup = 0.0;
    private double timeTotal = 0.0;
    private double timeStartSetUp = 0.0;
    private double timeStartSending = 0.0;
    private double timeEnd = 0.0;
    private double timeTaken = 0.0;


    // Record set up starting time
    public void recordTimeStartSetUp()
    {
        timeStartSetUp = System.currentTimeMillis();
    } // end method recordTimeStartSetUp

    // Record Set up time
    public void recordSetUpTime()
    {
        timeSetup = System.currentTimeMillis() - timeStartSetUp;
    } // end method recordSetUpTime

   // Record set up starting time
    public void recordTimeStartSending()
    {
        timeStartSending = System.currentTimeMillis();
    } // end method recordTimeStartSending

    // Record set up starting time
    public void recordTimeEnd()
    {
        timeEnd = System.currentTimeMillis();
    } // end method recordTimeStartSending

    // Record total time
    public void recordTimeTotal()
    {
        timeTotal = timeTotal + getTimeTaken();
    } // end method recordTimeStartSending

    // Return time taken to send and receive data
    public double getTimeStart()
    {
        return timeStartSending;
    } // end method getTimestart

    // Return end time
    public double getTimeEnd()
    {
        return timeEnd;
    } // end method getTimestart

    // Return start time taken for sending data
    public double getTimeTaken()
    {
        return timeTaken = timeEnd - timeStartSending;
    } // end method getTimeTaken

    // Return start time taken for sending data
    public double getTimeTotal()
    {
        return timeTotal;
    } // end method getTimeTaken


}
