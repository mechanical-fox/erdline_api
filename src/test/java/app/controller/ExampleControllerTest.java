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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.data.database.ExampleEntity;
import app.helper.Helper;
import app.repository.ExampleRepository;
import app.util.PasswordManager;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ExampleControllerTest {

    @Autowired
    MockMvc mockMvc;

	@Autowired
    private ExampleRepository exampleRepository;

	static boolean initialized = false;
	static String password = "password";

	/** A method to perform the initialization before each test. This function initialized the database, and the password 
	 * used by the API for admin operations.*/
	@BeforeEach
	void init(){

		PasswordManager.mockPassword(ExampleControllerTest.password);
		String htmlId = "e832cefda88b46b1av";
		String name = "API Unit Test";
		String html = "<p> Hello world </p>";
		ExampleEntity example = new ExampleEntity(htmlId, name, html);
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


	@Test
	@SuppressWarnings("null")
	void post_example_increase_size_by_one() throws Exception {

		String body = Helper.readAll("src/test/resources/body1.json");
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/documentation/html")
                        .contentType("application/json")
                        .content(body))
                            .andExpect(status().is(200))
							.andExpect(jsonPath("$.htmlId", notNullValue()))
							.andReturn();
		
		String answer = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(answer);
		String htmlId = jsonNode.get("htmlId").asText();

		mockMvc.perform(MockMvcRequestBuilders
                        .post("/example/htmlId/" + htmlId + "/saveAs/Doc Unit Testing")
                        .contentType("application/json")
						.header("Authorization",ExampleControllerTest.password))
                            .andExpect(status().is(201));

		mockMvc.perform(MockMvcRequestBuilders.get("/example"))
                            		.andExpect(status().is(200))
									.andExpect(jsonPath("$.length()", is(2)));
	}


	@Test
	@SuppressWarnings("null")
	void post_example_created_will_fields_expected() throws Exception {

		String body = Helper.readAll("src/test/resources/body1.json");
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/documentation/html")
                        .contentType("application/json")
                        .content(body))
                            .andExpect(status().is(200))
							.andExpect(jsonPath("$.htmlId", notNullValue()))
							.andReturn();
		
		String answer = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(answer);
		String htmlId = jsonNode.get("htmlId").asText();

		mockMvc.perform(MockMvcRequestBuilders
                        .post("/example/htmlId/" + htmlId + "/saveAs/Doc Unit Testing")
                        .contentType("application/json")
						.header("Authorization",ExampleControllerTest.password))
                            .andExpect(status().is(201));

		mockMvc.perform(MockMvcRequestBuilders.get("/example"))
                            		.andExpect(status().is(200))
									.andExpect(jsonPath("$.length()", is(2)))
									.andExpect(jsonPath("$[1].name", is("Doc Unit Testing")))
									.andExpect(jsonPath("$[1].htmlId", is(htmlId)))
									.andExpect(jsonPath("$[1].url_consultation", is("/access/" + htmlId)))
									.andExpect(jsonPath("$[1].url_download", is("/download/" + htmlId)));
		
		MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                        .get("/download/" + htmlId))
                            .andExpect(status().is(200))
							.andReturn();		

		String answer2 = result2.getResponse().getContentAsString();		
		boolean isHtmlReturnedCorrect = answer2.contains("<title>API Documentation</title>");	
		assertTrue(isHtmlReturnedCorrect);
	}

}
