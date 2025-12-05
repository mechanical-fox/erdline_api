package app.controller;


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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.helper.Helper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DocumentationControllerTests {

	@Autowired
    MockMvc mockMvc;

	@Test
	void doc_generated_presents_correct_parameters() throws Exception {
		String content = Helper.readAll("src/test/resources/body1.json");
		String body = content == null ? "" : content;
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/documentation/html")
                        .contentType("application/json")
                        .content(body))
                            .andExpect(status().is(200))
							.andReturn();
		
		String answer = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(answer);
		String url_consultation = jsonNode.get("url_consultation").asText();
		String url_consultation_casted = url_consultation == null ? "" : url_consultation;
		MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                        .get(url_consultation_casted))
                            .andExpect(status().is(200))
							.andReturn();
		
		String htmlGenerated = result2.getResponse().getContentAsString();
		boolean containsParameterName = htmlGenerated.contains("> project (Path) <");
		boolean containsParameterExample = htmlGenerated.contains("<i>Exemple: CustomerWS </i>");

		assertTrue(containsParameterName);
		assertTrue(containsParameterExample);
	}

	@Test
	void doc_generated_presents_correct_status_code() throws Exception {
		String content = Helper.readAll("src/test/resources/body1.json");
		String body = content == null ? "" : content;
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/documentation/html")
                        .contentType("application/json")
                        .content(body))
                            .andExpect(status().is(200))
							.andReturn();
		
		String answer = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(answer);
		String url_consultation = jsonNode.get("url_consultation").asText();
		String url_consultation_casted = url_consultation == null ? "" : url_consultation;
		MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                        .get(url_consultation_casted))
                            .andExpect(status().is(200))
							.andReturn();
		
		String htmlGenerated = result2.getResponse().getContentAsString();
		boolean containsCode204 = htmlGenerated.contains("> 204 <");
		boolean containsCode404 = htmlGenerated.contains("> 404 <");
		boolean containsCode412 = htmlGenerated.contains("> 412 <");

		assertTrue(containsCode204);
		assertTrue(containsCode404);
		assertFalse(containsCode412);
	}


	@Test
	void doc_generated_presents_correct_method_url() throws Exception {
		String content = Helper.readAll("src/test/resources/body1.json");
		String body = content == null ? "" : content;
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/documentation/html")
                        .contentType("application/json")
                        .content(body))
                            .andExpect(status().is(200))
							.andReturn();
		
		String answer = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(answer);
		String url_consultation = jsonNode.get("url_consultation").asText();
		String url_consultation_casted = url_consultation == null ? "" : url_consultation;
		MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                        .get(url_consultation_casted))
                            .andExpect(status().is(200))
							.andReturn();
		
		String htmlGenerated = result2.getResponse().getContentAsString();
		boolean containsCorrectUrl = htmlGenerated.contains("> /config/gatling/{project} <");
		boolean containsMethodPUT = htmlGenerated.contains("> PUT <");
		boolean containsMethodGET = htmlGenerated.contains("> GET <");

		assertTrue(containsCorrectUrl);
		assertTrue(containsMethodPUT);
		assertFalse(containsMethodGET);
	}


	@Test
	void doc_generated_presents_correct_tag() throws Exception {
		String content = Helper.readAll("src/test/resources/body1.json");
		String body = content == null ? "" : content;
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/documentation/html")
                        .contentType("application/json")
                        .content(body))
                            .andExpect(status().is(200))
							.andReturn();
		
		String answer = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(answer);
		String url_consultation = jsonNode.get("url_consultation").asText();
		String url_consultation_casted = url_consultation == null ? "" : url_consultation;
		MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                        .get(url_consultation_casted))
                            .andExpect(status().is(200))
							.andReturn();
		
		String htmlGenerated = result2.getResponse().getContentAsString();
		boolean containsCorrectTag = htmlGenerated.contains(">Configuration</a>");

		assertTrue(containsCorrectTag);
	}

}
