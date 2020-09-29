package FinalProject;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.*;

abstract  class StressTest{
    protected  Integer timeOut;
    protected  PrintStream out;
    protected  PrintStream err;
    protected  Boolean verbose;
    protected ArrayList<Tweet> data;

    StressTest(Integer timeOut) {
		this.timeOut = timeOut;
		out = System.out;
		err = System.err;
		verbose = true;
	}

    StressTest(Integer timeOut , PrintStream outputStream, PrintStream errorStream){
	this.timeOut = timeOut;
	this.out = outputStream;
	this.err = errorStream;
	verbose = true;
    }

    void setData(ArrayList<Tweet> tweets){
		this.data = tweets;
    }

    /* 
     *  Provide implementation of this (tester) method  for each test. 
     */
    abstract Boolean tester();
    
    public Boolean run(){
	StressTestCaller job = new StressTestCaller();
	final ExecutorService service = Executors.newSingleThreadExecutor();
	final Future<Boolean> futureResult  = service.submit(job);
	Boolean result=true;
	try{
	    result = futureResult.get(timeOut, TimeUnit.MILLISECONDS);// TimeUnit.SECONDS);
	}
	catch(TimeoutException te)
    {
    	this.out.println();
        this.out.println(te.getClass().getSimpleName()+ ": The test timed out.");
        futureResult.cancel(true);

        result = false;
    }
	catch(Exception e){
		this.out.println();
	    this.out.println(e.getClass().getSimpleName());
		this.out.println("Failed to execute stress test.\nException cause :" + e.getCause());
	    result = false;
	}
    finally {
        this.out.flush();
        this.err.flush();
        futureResult.cancel(true);
        service.shutdownNow();
        return result;
    }
    }

    private class StressTestCaller implements Callable<Boolean>{
	public StressTestCaller(){}
	public Boolean call(){
	    try{
		//time hack
		Boolean stressTestResult = tester();
		return stressTestResult;
	    }catch(Exception e){
		out.println("Exception: " + e);
		return false;
	    }
	}
    }
}
