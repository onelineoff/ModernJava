package org.moyoman.modernJava.service;

import java.util.concurrent.Callable;

/** A Callable wrapper for the Running Average class to allow for multi threaded execution.
 * 
 */
public class SharedRunningAverage implements Callable {

	private RunningAverage runningAverage;
	private int value;
	
	public SharedRunningAverage(RunningAverage runningAverage, int value) {
		this.runningAverage = runningAverage;
		this.value = value;
	}

	@Override
	public Object call() throws Exception {
		runningAverage.add(value);
		return runningAverage.getAverage();
	}
}
