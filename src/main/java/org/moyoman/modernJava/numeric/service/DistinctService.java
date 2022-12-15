package org.moyoman.modernJava.numeric.service;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.moyoman.modernJava.dto.DistinctEfficiencyDto;
import org.moyoman.modernJava.dto.MsecDuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** Find the number of distinct values in an array.
 *  Provide both stream and non-stream implementations for comparison.
 */
@Service
public class DistinctService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DistinctService.class);
	
	/** Find the number of distinct elements in the array using streams.
	 * 
	 * @param arr The array of integers
	 * @return A count of the number of distinct elements.
	 */
	public long findDistinctUsingStreams(Integer[] arr) {
		long count = Arrays.stream(arr).distinct().count();
		LOGGER.info("findDistinctUsingStreams: for array of size {}, {} are distinct", arr.length, count);
		return count;
	}
	
	/** Find the number of distinct elements in the array using Java 7 features.
	 * 
	 * @param arr The array of integers
	 * @return A count of the number of distinct elements.
	 */
	public long findDistinctNonFP(Integer[] arr) {
		List<Integer> elementList = Arrays.asList(arr);
		Set<Integer> elementSet = new HashSet<>();
		elementSet.addAll(elementList);
		
		long count = elementSet.size();
		LOGGER.info("findDistinctUsingStreams: for array of size {}, {} are distinct", arr.length, count);
		return count;
	}
	
	/** Determine the efficiency of different implementations for the given input size.
	 *  Generate random values based on the input parameter, run the different implementations,
	 *  and set the time for each.
	 *  
	 * @param size The number of random values to use.
	 * @return The dto with the timing information.
	 */
	public DistinctEfficiencyDto testEfficiency(int size) {
		DistinctEfficiencyDto dto = new DistinctEfficiencyDto();
		dto.setSize(size);
		
		// Generate a large amount of random test data to use for the two different methods.
		Random random = new Random(Instant.now().getEpochSecond());
		Integer[] dataArray = new Integer[size];
		Set<Integer> elementSet = new HashSet<>();
		
		int maxValue = (int) (size * 1.1f);
		for (int i=0; i<size; i++) {
			dataArray[i] = random.nextInt(maxValue + 1);
			elementSet.add(dataArray[i]);
		}
		
		Instant start = Instant.now();
		long distinctCount = findDistinctUsingStreams(dataArray);
		Instant end = Instant.now();
		MsecDuration duration = new MsecDuration(start, end);
		LOGGER.info("findDistinctUsingStreams found {} distinct of {}, in {}", distinctCount, size, duration);
		
		dto.setStreamTimeMsec(duration.getTotalMsec());
		start = Instant.now();
		long nonFpDistinctCount = findDistinctNonFP(dataArray);
		end = Instant.now();
		duration = new MsecDuration(start, end);
		LOGGER.info("findDistinctNonFP found {} distinct of {}, in {}", nonFpDistinctCount, size, duration);
		dto.setJava7TimeMsec(duration.getTotalMsec());
		
		return dto;
	}
}
