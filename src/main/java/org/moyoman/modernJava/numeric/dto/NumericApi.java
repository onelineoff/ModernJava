package org.moyoman.modernJava.numeric.dto;

import org.moyoman.modernJava.numeric.service.BinaryGapService;
import org.moyoman.modernJava.numeric.service.DistinctService;
import org.moyoman.modernJava.numeric.service.FindFirstService;
import org.moyoman.modernJava.numeric.service.MaxSumService;
import org.moyoman.modernJava.numeric.service.MinimumMovesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/numeric/")
/** Expose some subset of the numeric services.
 *  This is all for illustration purposes, not meant to do anything useful.
 */
public class NumericApi {
	@Autowired
	private BinaryGapService binaryGapService;
	@Autowired
	private DistinctService distinctService;
	@Autowired
	private FindFirstService findFirstService;
	@Autowired
	private MaxSumService maxSumService;
	@Autowired
	private MinimumMovesService minimumMovesService;
	
	@GetMapping(value="binaryGap/{value}", produces="application/json")
	public ResponseEntity<Integer> getBinaryGap(@PathVariable(name="value") int value) {
		Integer binaryGapValue = binaryGapService.betterSolution(value);
		return ResponseEntity.ok(binaryGapValue);
		
	}
}
