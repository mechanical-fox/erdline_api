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

import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;

import app.model.database.ColorEntity;
import app.model.database.SessionEntity;
import app.model.database.TokenEntity;
import app.repository.ColorRepository;
import app.repository.SessionRepository;
import app.repository.TokenRepository;
import helper.Helper;

import static org.hamcrest.Matchers.is;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ColorControllerTest {

    @Autowired
    MockMvc mockMvc;

	@Autowired
    private ColorRepository colorRepository;

	@Autowired
    private SessionRepository sessionRepository;

	@Autowired
    private TokenRepository tokenRepository;

	static final String TEST_TOKEN = "1171660c4f41406d";


	/** A method to perform the initialization before each test. This function initialized the database, and its content.*/
	@BeforeEach
	void init() throws NoSuchAlgorithmException{

		String hash = Helper.hash("password");
		OffsetDateTime expirationDate = OffsetDateTime.now().plusSeconds(3600);

		ColorEntity color1 = new ColorEntity("Orange", "rgb(240, 138, 22)", "rgb(231, 195, 36)");
		ColorEntity color2 = new ColorEntity("Noir", "rgb(32, 32, 32)", "rgb(97, 97, 97)");
		ColorEntity color3 = new ColorEntity("Bleu", "rgb(21, 59, 226)", "rgb(44, 141, 206)");
		SessionEntity session = new SessionEntity("MysticalAshes", hash, true, null, null, null);
		TokenEntity token = new TokenEntity(ColorControllerTest.TEST_TOKEN, session, expirationDate);

		this.colorRepository.deleteAll();
		this.colorRepository.save(color1);
		this.colorRepository.save(color2);
		this.colorRepository.save(color3);
		this.sessionRepository.deleteAll();
		this.sessionRepository.save(session);
		this.tokenRepository.deleteAll();
		this.tokenRepository.save(token);
	}

	
	@Test
	@SuppressWarnings("null")
	void testListColors() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders
                        	.get("/color"))
                            	.andExpect(status().is(200))
								.andExpect(jsonPath("$.length()", is(3)))
								.andExpect(jsonPath("$[0].name", is("Orange")))
								.andExpect(jsonPath("$[0].firstGradient", is("rgb(240, 138, 22)")))
								.andExpect(jsonPath("$[0].secondGradient", is("rgb(231, 195, 36)")))
								.andExpect(jsonPath("$[1].name", is("Noir")))
								.andExpect(jsonPath("$[1].firstGradient", is("rgb(32, 32, 32)")))
								.andExpect(jsonPath("$[1].secondGradient", is("rgb(97, 97, 97)")));
							
		
	}


	@Test
	@SuppressWarnings("null")
	void testGetColorById() throws Exception {

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        	.get("/color"))
                            	.andExpect(status().is(200))
								.andReturn();

		String answer = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode json = objectMapper.readTree(answer);
		JsonNode thirdItem = json.get(2);
		Integer id = thirdItem.get("id").asInt();
		
		mockMvc.perform(MockMvcRequestBuilders
                        	.get("/color/" + id))
                            	.andExpect(status().is(200))
								.andExpect(jsonPath("$.name", is("Bleu")))
								.andExpect(jsonPath("$.firstGradient", is("rgb(21, 59, 226)")))
								.andExpect(jsonPath("$.secondGradient", is("rgb(44, 141, 206)")));
							
	}


	@Test
	@SuppressWarnings("null")
	void testCreateColor() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders
                        	.get("/color"))
                            	.andExpect(status().is(200))
								.andExpect(jsonPath("$.length()", is(3)));

		String content = Helper.readAll("src/test/resources/bodyColor.json");
        String body = content == null ? "" : content;

		mockMvc.perform(MockMvcRequestBuilders
                    .post("/color")
                    .contentType("application/json")
					.header("Authorization", "Bearer " + TEST_TOKEN)
                    .content(body))
                    	.andExpect(status().is(201));

		mockMvc.perform(MockMvcRequestBuilders
                .get("/color"))
                	.andExpect(status().is(200))
					.andExpect(jsonPath("$.length()", is(4)))
					.andExpect(jsonPath("$[3].name", is("Vert")))
					.andExpect(jsonPath("$[3].firstGradient", is("rgb(36, 180, 48)")))
					.andExpect(jsonPath("$[3].secondGradient", is("rgb(89, 243, 102)")));
							
	}


 	@Test
	@SuppressWarnings("null")
	void testModifyColor() throws Exception {
		
		MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                        	.get("/color"))
                            	.andExpect(status().is(200))
								.andExpect(jsonPath("$.length()", is(3)))
								.andExpect(jsonPath("$[1].name", is("Noir")))
								.andExpect(jsonPath("$[1].firstGradient", is("rgb(32, 32, 32)")))
								.andExpect(jsonPath("$[1].secondGradient", is("rgb(97, 97, 97)")))
								.andReturn();

		String answer = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode json = objectMapper.readTree(answer);
		JsonNode secondItem = json.get(1);
		Integer id = secondItem.get("id").asInt();

		String content = Helper.readAll("src/test/resources/bodyColor.json");
        String body = content == null ? "" : content;

		mockMvc.perform(MockMvcRequestBuilders
                    .put("/color/" + id)
                    .contentType("application/json")
					.header("Authorization", "Bearer " + TEST_TOKEN)
                    .content(body))
                    	.andExpect(status().is(204));

		mockMvc.perform(MockMvcRequestBuilders
                .get("/color"))
                	.andExpect(status().is(200))
					.andExpect(jsonPath("$.length()", is(3)))
					.andExpect(jsonPath("$[1].name", is("Vert")))
					.andExpect(jsonPath("$[1].firstGradient", is("rgb(36, 180, 48)")))
					.andExpect(jsonPath("$[1].secondGradient", is("rgb(89, 243, 102)")));
							
	}




}
