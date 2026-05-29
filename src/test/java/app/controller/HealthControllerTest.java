package app.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.hamcrest.Matchers.is;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class HealthControllerTest {

    @Autowired
    MockMvc mockMvc;


	@Test
	@SuppressWarnings("null")
	void testHealthReturnStatusRunning() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders
                        	.get("/health"))
                            	.andExpect(status().is(200))
								.andExpect(jsonPath("$.status", is("Running")));
	
	}


}
