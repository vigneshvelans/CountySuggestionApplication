package com.app.counties;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CountiesApiApplicationTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testLoadCounties() throws Exception {
		InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");
		InputStreamReader isReader = new InputStreamReader(inputStream);
	    BufferedReader reader = new BufferedReader(isReader);
	    StringBuffer sb = new StringBuffer();
	    String str;
	    while((str = reader.readLine())!= null){
	    	sb.append(str);
	    }
		
    	mvc.perform(MockMvcRequestBuilders
    		      .post("/saveCounties")
    		      .content(sb.toString())
    		      .contentType(MediaType.APPLICATION_JSON)
    		      .accept(MediaType.APPLICATION_JSON))
    	      .andExpect(status().isCreated());
	}
	
	@Test
	public void testGetCounty() throws Exception {
		mvc.perform(MockMvcRequestBuilders
	  			.get("/CountySuggestion/CO")
	  			.accept(MediaType.APPLICATION_JSON))
	      .andDo(print())
	      .andExpect(status().isOk());	
	}

}
