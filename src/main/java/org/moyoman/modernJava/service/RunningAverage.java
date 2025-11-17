package org.moyoman.modernJava.service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/** Thread safe class which holds the number of entries, and their sum.
 * 
 * 
 */
public class RunningAverage {
	private AtomicInteger count;
	private AtomicLong total;

	public static void main(String[] args) {
		RunningAverage ra = new RunningAverage();
		ra.add(1);
		ra.add(2);
		ra.add(3);
		System.out.println(ra.getAverage()); 
		
		ra = new RunningAverage();
		ra.add(5);
		ra.add(10);
		System.out.println(ra.getAverage()); // Output: 7.5
		ra.add(15);
		System.out.println(ra.getAverage()); // Output: 10.0
		ra = new RunningAverage();
		System.out.println(ra.getAverage()); // Output: 0.0
	}

	public RunningAverage() {
		count = new AtomicInteger(0);
		total = new AtomicLong(0);
	}
	
	/** Add the value in a thread safe manner.
	 * 
	 * @param value The value to be added.
	 */
	public void add(int value) {
		count.getAndIncrement();
		total.addAndGet(value);
	}

	/** Get the current average, which is total / count, or 0 if no entries yet.
	 * 
	 * @return
	 */
	public double getAverage() {
		if (count.get() == 0) {
			return 0.0;
		}
		else {
			return (double ) total.get() / count.get();
		}
	}
}
