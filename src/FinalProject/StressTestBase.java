package FinalProject;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


abstract class StressTestBase {
	protected Integer timeOut;
	protected PrintStream out;
	protected PrintStream err;
	protected Boolean verbose;
	protected ArrayList<Tweet> tweets;
	protected ArrayList<String> stopWords;

	StressTestBase(Integer timeOut) {
		this.timeOut = timeOut;
		out = System.out;
		err = System.err;
		verbose = true;
	}
	
	StressTestBase(Integer timeOut , PrintStream outputStream, PrintStream errorStream){
		this.timeOut = timeOut;
		this.out = outputStream;
		this.err = errorStream;
		verbose = true;
	}

	void setData(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		this.tweets = tweets;
		this.stopWords = stopWords;
	}

	/*
	 * Provide implementation of this (tester) method for each test.
	 */
	abstract Boolean tester();

	public Long run() {
		StressTestCaller job = new StressTestCaller();
		final ExecutorService service = Executors.newSingleThreadExecutor();
		final Future<Long> futureResult = service.submit(job);
		Long result = 0l;
		try {
			result = futureResult.get(timeOut, TimeUnit.MILLISECONDS);// TimeUnit.SECONDS);
			service.shutdown();
		} catch (Exception e) {
			e.printStackTrace(out);
			result = (new Long(StressTestBase.this.timeOut)) * 5;
			service.shutdown();
		}
		return result;

	}

	private class StressTestCaller implements Callable<Long> {
		public StressTestCaller() {
		}

		public Long call() {
			try {
				// time hack
				Long startTime = System.currentTimeMillis();
				tester();
				return System.currentTimeMillis() - startTime;
			} catch (Exception e) {
				e.printStackTrace(out);
				return (new Long(StressTestBase.this.timeOut)) * 5;
			}
		}
	}
}
