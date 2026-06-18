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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;


import app.model.database.SessionEntity;
import app.model.database.SpriteEntity;
import app.model.database.TokenEntity;
import app.repository.SessionRepository;
import app.repository.SpriteRepository;
import app.repository.TokenRepository;
import helper.Helper;

import static org.hamcrest.Matchers.is;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SpriteControllerTest {

    @Autowired
    private MockMvc mockMvc;
	@Autowired
    private SpriteRepository spriteRepository;
	@Autowired
    private SessionRepository sessionRepository;
	@Autowired
    private TokenRepository tokenRepository;

	static private final String TEST_TOKEN = "1171660c4f41406d";


	@BeforeEach
	public void init() throws NoSuchAlgorithmException{

		String hash = Helper.hash("password");
		OffsetDateTime expirationDate = OffsetDateTime.now().plusSeconds(3600);
        String data1 = "data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4";
        String data2 = "data:image/png;base64, iVBORw0KGgoAAAANSUhBK0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";

        SpriteEntity sprite1 = new SpriteEntity("Adrien", "Adrien.png", data1);
        SpriteEntity sprite2 = new SpriteEntity("Grace", "Grace.png", data2);
		SessionEntity session = new SessionEntity("MysticalAshes", hash, true, null, null, null);
		TokenEntity token = new TokenEntity(SpriteControllerTest.TEST_TOKEN, session, expirationDate);

        this.spriteRepository.deleteAll();
        this.spriteRepository.save(sprite1);
        this.spriteRepository.save(sprite2);
		this.sessionRepository.deleteAll();
		this.sessionRepository.save(session);
		this.tokenRepository.deleteAll();
		this.tokenRepository.save(token);
	}

    @Test
	@SuppressWarnings("null")
	public void testListSprites() throws Exception {

        String data1 = "data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4";
        String data2 = "data:image/png;base64, iVBORw0KGgoAAAANSUhBK0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";
		
		mockMvc.perform(MockMvcRequestBuilders
                        	.get("/sprite"))
                            	.andExpect(status().is(200))
								.andExpect(jsonPath("$.length()", is(2)))
								.andExpect(jsonPath("$[0].name", is("Adrien")))
								.andExpect(jsonPath("$[0].filename", is("Adrien.png")))
								.andExpect(jsonPath("$[0].data", is(data1)))
								.andExpect(jsonPath("$[1].name", is("Grace")))
								.andExpect(jsonPath("$[1].filename", is("Grace.png")))
								.andExpect(jsonPath("$[1].data", is(data2)));
							
		
	}

    @Test
	@SuppressWarnings("null")
	public void testGetSpriteById() throws Exception {

        String data = "data:image/png;base64, iVBORw0KGgoAAAANSUhBK0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        	.get("/sprite"))
                            	.andExpect(status().is(200))
								.andReturn();

		String answer = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode json = objectMapper.readTree(answer);
		JsonNode secondItem = json.get(1);
		Integer id = secondItem.get("id").asInt();
		
		mockMvc.perform(MockMvcRequestBuilders
                        	.get("/sprite/" + id))
                            	.andExpect(status().is(200))
								.andExpect(jsonPath("$.name", is("Grace")))
								.andExpect(jsonPath("$.filename", is("Grace.png")))
								.andExpect(jsonPath("$.data", is(data)));
							
	}

	@Test
	@SuppressWarnings("null")
	public void testCreateSprite() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders
                        	.get("/sprite"))
                            	.andExpect(status().is(200))
								.andExpect(jsonPath("$.length()", is(2)));

		String content = Helper.readAll("src/test/resources/bodySprite.json");
        String body = content == null ? "" : content;

		mockMvc.perform(MockMvcRequestBuilders
                    .post("/sprite")
                    .contentType("application/json")
					.header("Authorization", "Bearer " + TEST_TOKEN)
                    .content(body))
                    	.andExpect(status().is(201));

		mockMvc.perform(MockMvcRequestBuilders
                .get("/sprite"))
                	.andExpect(status().is(200))
					.andExpect(jsonPath("$.length()", is(3)))
					.andExpect(jsonPath("$[2].name", is("Nathaniel")))
					.andExpect(jsonPath("$[2].filename", is("barman.png")))
					.andExpect(jsonPath("$[2].data", is("data:image/png;base64, iVBORw0KGgAACNbyQI12P4//8/w38GIAXDIBKE0DU5ErkJggg==")));
							
	}

 	@Test
	@SuppressWarnings("null")
	public void testModifySprite() throws Exception {

        String data = "data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4";
        String newData = "data:image/png;base64, iVBORw0KGgAACNbyQI12P4//8/w38GIAXDIBKE0DU5ErkJggg==";
		
		MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                        	.get("/sprite"))
                            	.andExpect(status().is(200))
								.andExpect(jsonPath("$.length()", is(2)))
								.andExpect(jsonPath("$[0].name", is("Adrien")))
								.andExpect(jsonPath("$[0].filename", is("Adrien.png")))
								.andExpect(jsonPath("$[0].data", is(data)))
								.andReturn();

		String answer = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode json = objectMapper.readTree(answer);
		JsonNode firstItem = json.get(0);
		Integer id = firstItem.get("id").asInt();

		String content = Helper.readAll("src/test/resources/bodySprite.json");
        String body = content == null ? "" : content;

		mockMvc.perform(MockMvcRequestBuilders
                    .put("/sprite/" + id)
                    .contentType("application/json")
					.header("Authorization", "Bearer " + TEST_TOKEN)
                    .content(body))
                    	.andExpect(status().is(204));

		mockMvc.perform(MockMvcRequestBuilders
                .get("/sprite"))
                	.andExpect(status().is(200))
					.andExpect(jsonPath("$.length()", is(2)))
					.andExpect(jsonPath("$[0].name", is("Nathaniel")))
					.andExpect(jsonPath("$[0].filename", is("barman.png")))
					.andExpect(jsonPath("$[0].data", is(newData)));
							
	}

}
