package com.app.counties.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.counties.model.County;
import com.app.counties.repository.CountyRepositoryService;

@RestController
public class CountyController {
	
	private static final String RESPONSE_SAVE = "County Data saved successfully..,";
	
	private static final String COMMA = ",";
	
	@Autowired
	private CountyRepositoryService daService;
	
	@PostMapping("/saveCounties")
	public String saveCounties(@RequestBody List<County> cties) {
		daService.saveAll(cties);		
		return RESPONSE_SAVE;
	}
	
	@GetMapping("/CountySuggestion/{queryString}")
	public List<County> getCounties(@PathVariable String queryString) {		
		List<County> cties = new ArrayList<>();
		if(queryString.contains(COMMA)) {
			String name = queryString.split(COMMA)[0];
			String state = queryString.split(COMMA)[1];
			cties.addAll(daService.findByStateAndName(state, name));
			cties.addAll(daService.findByStateAndName(name, state));
		}
		else {
			cties.addAll(daService.findByNameStartingWithIgnoreCase(queryString));
			cties.addAll(daService.findByStateStartingWithIgnoreCase(queryString));
		}		
		return cties.stream().limit(5).collect(Collectors.toList());
	}

}
