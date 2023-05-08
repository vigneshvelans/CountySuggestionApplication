package com.app.counties;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.app.counties.model.County;
import com.app.counties.repository.CountyRepositoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class CountiesApiApplication {
	
	@Autowired
	private CountyRepositoryService daService;

	public static void main(String[] args) {
		SpringApplication.run(CountiesApiApplication.class, args);
	}
	
	@Bean
	InitializingBean loadData() {
	    return () -> {
	    	TypeReference<List<County>> typeRef = new TypeReference<List<County>>(){};
	    	try {
	    		InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");
	    		ObjectMapper objectMapper = new ObjectMapper();
	    		List<County> counties = objectMapper.readValue(inputStream, typeRef);
	    		daService.saveAll(counties);
	    		System.out.println("Data Saved sucessfully..,");
	    	}
	    	catch(IOException e) {
	    		System.out.println("Unable to save data..," + e.getMessage());
	    		throw new Exception(e);
	    	}
	    };
	}

}
