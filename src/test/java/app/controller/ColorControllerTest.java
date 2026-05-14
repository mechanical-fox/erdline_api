package app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.model.database.ColorEntity;
import app.repository.ColorRepository;

import static org.hamcrest.Matchers.is;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ColorControllerTest {

    @Autowired
    MockMvc mockMvc;

	@Autowired
    private ColorRepository exampleRepository;

	static boolean initialized = false;
	static String password = "password";

	/** A method to perform the initialization before each test. This function initialized the database, and its content.*/
	@BeforeEach
	void init(){


		ColorEntity example = new ColorEntity("Orange", "rgb(240, 138, 22)", "rgb(231, 195, 36)");
		this.exampleRepository.deleteAll();
		this.exampleRepository.save(example);
		
	}


	@Test
	@SuppressWarnings("null")
	void get_example_have_fields_expected() throws Exception {
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        				.get("/example"))
                            			.andExpect(status().is(200))
										.andExpect(jsonPath("$.length()", is(1)))
										.andExpect(jsonPath("$[0].htmlId", is("e832cefda88b46b1a")))
										.andExpect(jsonPath("$[0].name", is("API Unit Test")))
										.andExpect(jsonPath("$[0].url_consultation", is("/access/e832cefda88b46b1a")))
										.andExpect(jsonPath("$[0].url_download", is("/download/e832cefda88b46b1a")))
										.andReturn();
		
		String answer = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode json = objectMapper.readTree(answer);
		JsonNode firstItem = json.get(0);
		String url_download = firstItem.get("url_download").asText();

		MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
											.get(url_download))
                            				.andExpect(status().is(200))
											.andReturn();
		
		String html = result2.getResponse().getContentAsString();
		assertEquals("<p> Hello world </p>", html);
	}


}
