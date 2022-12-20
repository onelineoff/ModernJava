package org.moyoman.modernJava.numeric.controller;

import java.util.List;

import org.moyoman.modernJava.dto.DistinctEfficiencyDto;
import org.moyoman.modernJava.dto.FindFirstEfficiencyDto;
import org.moyoman.modernJava.numeric.service.BinaryGapService;
import org.moyoman.modernJava.numeric.service.DistinctService;
import org.moyoman.modernJava.numeric.service.FindFirstService;
import org.moyoman.modernJava.numeric.service.MaxSumService;
import org.moyoman.modernJava.numeric.service.MinimumMovesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping(value="distinct/efficiency/{size}", produces="application/json")
	public ResponseEntity<DistinctEfficiencyDto> exerciseDistinctService(@PathVariable(name="size") int size) {
		return ResponseEntity.ok(distinctService.testEfficiency(size));
	}
	
	@GetMapping(value="findFirst/efficiency/{size}", produces="application/json")
	public ResponseEntity<FindFirstEfficiencyDto> exerciseFindFirstService(@PathVariable(name="size") int size) {
		FindFirstEfficiencyDto dto = findFirstService.testEfficiency(size);
		if (dto.isSuccessful()) {
			return ResponseEntity.ok(dto);
		}
		else {
			return ResponseEntity.internalServerError().body(dto);
		}
	}
	
	@GetMapping(value="maxSum/{size}/{values}")
	// TODO values is a csv list of integers.  Need to implement this better.
	// Upload csv file is one possibility.
	public ResponseEntity<Integer> getMaxSum(@PathVariable(name="size") int size, @PathVariable(name="values") String values) {
		String[] sarr = values.split(",");
		try {
			Integer[] iarr = new Integer[sarr.length];
			for (int i=0; i<sarr.length; i++) {
				iarr[i] = Integer.parseInt(sarr[i]);
			}
			
			return ResponseEntity.ok(maxSumService.getMaxValue(iarr, size));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}		
	}
	
	// TODO Similar issue as getMaxSum endpoint.
	@GetMapping(value="minimumMoves/{values}")
	public ResponseEntity<Integer> getMinimumMoves(@PathVariable(name="values") String values) {
		String[] sarr = values.split(",");
		try {
			Integer[] iarr = new Integer[sarr.length];
			for (int i=0; i<sarr.length; i++) {
				iarr[i] = Integer.parseInt(sarr[i]);
			}
			
			return ResponseEntity.ok(minimumMovesService.getMinimumMoves(iarr));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}		
	}
}
