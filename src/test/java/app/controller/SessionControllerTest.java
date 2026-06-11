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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;


import app.model.database.SessionEntity;
import app.model.database.TokenEntity;
import app.repository.SessionRepository;
import app.repository.TokenRepository;
import helper.Helper;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SessionControllerTest {
    

    @Autowired
    private MockMvc mockMvc;
	@Autowired
    private SessionRepository sessionRepository;
	@Autowired
    private TokenRepository tokenRepository;

    static private final String TEST_TOKEN = "1171660c4f41406d";


    @BeforeEach
	public void init() throws NoSuchAlgorithmException{

		String hash = Helper.hash("sKyrIm-4678");
		OffsetDateTime expirationDate = OffsetDateTime.now().plusSeconds(3600);
        String json_background = "[{\"counter\":1,\"id\":\"background-1\",\"name\":\"Guilde\",\"color_id\":1}," 
            + "{\"counter\":2,\"id\":\"background-2\",\"name\":\"Lac\",\"color_id\":3}]";
        String json_characters = "[{\"counter\":1,\"id\":\"character-1\",\"name\":\"Adrien\",\"expressions\":"
            + "[{\"id\":\"expr-1\",\"counter\":1,\"name\":\"Joie\",\"sprite_id\":\"1\"}]},"
            + "{\"counter\":2,\"id\":\"character-2\",\"name\":\"Grace\",\"expressions\":"
            + "[{\"id\":\"expr-1\",\"counter\":1,\"name\":\"Joie\",\"sprite_id\":\"2\"}]}]";
        String json_scenes = "[{\"createdAt\":1779644345575,\"counter\":1,\"id\":\"scene-1\",\"name\":\"Introduction\","
            + "\"backgroundId\":\"background-1\",\"messages\":[{\"characterId\":\"character-1\",\"expressionId\":\"expr-1\","
            + "\"text\":\"Bonjour à tous, chers membres de la Guilde. Je me réjouis de vous voir si nombreux ce "
            + "soir.\",\"nextSceneId\":null},{\"characterId\":\"character-2\",\"expressionId\":\"expr-1\",\"text\":\"Bonjour.\","
            + "\"nextSceneId\":null},{\"characterId\":\"narration\",\"expressionId\":null,\"text\":\"Adrien regarde alors "
            + "autour de lui. Puis se rend compte que la Guilde des aventuriers est quelque peu déserte.\","
            + "\"nextSceneId\":null}]}]";

		SessionEntity session = new SessionEntity("MysticalAshes", hash, true, json_background, json_characters, json_scenes);
		TokenEntity token = new TokenEntity(SessionControllerTest.TEST_TOKEN, session, expirationDate);

		this.sessionRepository.deleteAll();
		this.sessionRepository.save(session);
		this.tokenRepository.deleteAll();
		this.tokenRepository.save(token);
	}


    @Test
	@SuppressWarnings("null")
    public void testRequestToken() throws Exception{

        String content = Helper.readAll("src/test/resources/bodyAuth.json");
        String body = content == null ? "" : content;

        mockMvc.perform(MockMvcRequestBuilders
                            .post("/auth")
                            .contentType("application/json")
                            .content(body))
                                .andExpect(status().is(200))
					            .andExpect(jsonPath("$.token", notNullValue()))
                                .andExpect(jsonPath("$.expireIn", is(3600)))
                                .andExpect(jsonPath("$.authorizationType", is("Bearer Authentication")))
                                .andExpect(jsonPath("$.sessionId", notNullValue()))
                                .andExpect(jsonPath("$.isAdmin", is(true)));
    }

    @Test
    public void testRequestTokenBadPassword() throws Exception{

        String body = "{ \"session\": \"MysticalAshes\", \"password\": \"Exploit\"}";

        mockMvc.perform(MockMvcRequestBuilders
                            .post("/auth")
                            .contentType("application/json")
                            .content(body))
                                .andExpect(status().is(401))
                                .andExpect(content().string("Password incorrect"));
    }

    @Test
    public void testRequestTokenFieldsMissing() throws Exception{

        String body = "{ \"session\": \"MysticalAshes\"}";

        mockMvc.perform(MockMvcRequestBuilders
                            .post("/auth")
                            .contentType("application/json")
                            .content(body))
                                .andExpect(status().is(401))
                                .andExpect(content().string("The following fields are mandatories: session, password"));
    }

    @Test
    public void testRequestTokenSessionInexisting() throws Exception{

        String body = "{ \"session\": \"Anonymous\", \"password\": \"Red-46-Green-68-Azur\"}";

        mockMvc.perform(MockMvcRequestBuilders
                            .post("/auth")
                            .contentType("application/json")
                            .content(body))
                                .andExpect(status().is(401))
                                .andExpect(content().string("Session not Found"));
    }


    @Test
	@SuppressWarnings("null")
    public void testCreateSession() throws Exception{

        String content = Helper.readAll("src/test/resources/bodySession.json");
        String body = content == null ? "" : content;

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/session")
                        .contentType("application/json")
                        .content(body))
                        .andExpect(status().is(201))
                        .andReturn();

        String body2 = "{\"session\" : \"Unicorn\", \"password\": \"Sparkles4\"}";

        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                            .post("/auth")
                            .contentType("application/json")
                            .content(body2))
                                .andExpect(status().is(200))
					            .andExpect(jsonPath("$.token", notNullValue()))
                                .andExpect(jsonPath("$.sessionId", notNullValue()))
                                .andReturn();

        String answer = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode json = objectMapper.readTree(answer);
        Integer id = json.get("sessionId").asInt();
        String token = json.get("token").asText();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/session/" + id)
                        .header("Authorization", "Bearer " + token))
                            .andExpect(status().is(200))
                            .andExpect(jsonPath("$.id", is(id)))
                            .andExpect(jsonPath("$.session", is("Unicorn")))
                            .andExpect(jsonPath("$.password", is("******")))
                            .andExpect(jsonPath("$.isAdmin", is(false)))
                            .andExpect(jsonPath("$.json_backgrounds", containsString("\"name\":\"Jardin\",\"color_id\":1}]")))
                            .andExpect(jsonPath("$.json_characters", containsString("\"counter\":1,\"name\":\"Gratitude\",\"sprite_id\":\"1\"}]}")))
                            .andExpect(jsonPath("$.json_scenes", containsString(",\"counter\":1,\"id\":\"scene-1\",\"name\":\"Meeting\"")));


    }



    @Test
	@SuppressWarnings("null")
    public void testGetSessionById() throws Exception{

        String content = Helper.readAll("src/test/resources/bodyAuth.json");
        String body = content == null ? "" : content;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                            .post("/auth")
                            .contentType("application/json")
                            .content(body))
                                .andExpect(status().is(200))
					            .andExpect(jsonPath("$.token", notNullValue()))
                                .andExpect(jsonPath("$.expireIn", is(3600)))
                                .andExpect(jsonPath("$.authorizationType", is("Bearer Authentication")))
                                .andExpect(jsonPath("$.sessionId", notNullValue()))
                                .andExpect(jsonPath("$.isAdmin", is(true)))
                                .andReturn();

        String answer = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode json = objectMapper.readTree(answer);
        Integer id = json.get("sessionId").asInt();
        String token = json.get("token").asText();

        mockMvc.perform(MockMvcRequestBuilders
                    .get("/session/" + id)
                    .header("Authorization", "Bearer " + token))
                        .andExpect(status().is(200))
                        .andExpect(jsonPath("$.id", is(id)))
                        .andExpect(jsonPath("$.session", is("MysticalAshes")))
                        .andExpect(jsonPath("$.password", is("******")))
                        .andExpect(jsonPath("$.isAdmin", is(true)))
                        .andExpect(jsonPath("$.json_backgrounds", containsString("\"name\":\"Guilde\",\"color_id\":1}")))
                        .andExpect(jsonPath("$.json_characters", containsString("\"counter\":1,\"name\":\"Joie\",\"sprite_id\":\"2\"}]}")))
                        .andExpect(jsonPath("$.json_scenes", containsString("Adrien regarde alors autour de lui. Puis se rend compte que")));

    }

    @Test
	@SuppressWarnings("null")
    public void testPatchSession() throws Exception{

        String content = Helper.readAll("src/test/resources/bodyAuth.json");
        String body = content == null ? "" : content;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                            .post("/auth")
                            .contentType("application/json")
                            .content(body))
                                .andExpect(status().is(200))
					            .andExpect(jsonPath("$.token", notNullValue()))
                                .andExpect(jsonPath("$.expireIn", is(3600)))
                                .andExpect(jsonPath("$.authorizationType", is("Bearer Authentication")))
                                .andExpect(jsonPath("$.sessionId", notNullValue()))
                                .andExpect(jsonPath("$.isAdmin", is(true)))
                                .andReturn();

        String answer = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode json = objectMapper.readTree(answer);
        Integer id = json.get("sessionId").asInt();
        String token = json.get("token").asText();

        String content2 = Helper.readAll("src/test/resources/bodyPatch.json");
        String body2 = content2 == null ? "" : content2;

        mockMvc.perform(MockMvcRequestBuilders
                    .get("/session/" + id)
                    .header("Authorization", "Bearer " + token))
                        .andExpect(status().is(200))
                        .andExpect(jsonPath("$.id", is(id)))
                        .andExpect(jsonPath("$.session", is("MysticalAshes")))
                        .andExpect(jsonPath("$.password", is("******")))
                        .andExpect(jsonPath("$.isAdmin", is(true)))
                        .andExpect(jsonPath("$.json_backgrounds", containsString("\"name\":\"Guilde\",\"color_id\":1}")))
                        .andExpect(jsonPath("$.json_characters", containsString("\"counter\":1,\"name\":\"Joie\",\"sprite_id\":\"2\"}]}")))
                        .andExpect(jsonPath("$.json_scenes", containsString("Adrien regarde alors autour de lui. Puis se rend compte que")));

        mockMvc.perform(MockMvcRequestBuilders
                    .patch("/session/" + id)
                    .header("Authorization", "Bearer " + token)
                    .contentType("application/json")
                    .content(body2))
                        .andExpect(status().is(204));

        mockMvc.perform(MockMvcRequestBuilders
                    .get("/session/" + id)
                    .header("Authorization", "Bearer " + token))
                        .andExpect(status().is(200))
                        .andExpect(jsonPath("$.id", is(id)))
                        .andExpect(jsonPath("$.session", is("MysticalAshes")))
                        .andExpect(jsonPath("$.password", is("******")))
                        .andExpect(jsonPath("$.isAdmin", is(true)))
                        .andExpect(jsonPath("$.json_backgrounds", containsString("\"name\" : \"Study\", \"color_id\" : 5")))
                        .andExpect(jsonPath("$.json_characters", containsString(" \"id\" : \"mini-boss\", \"name\": \"Lorenzo\"")))
                        .andExpect(jsonPath("$.json_scenes", containsString("Adrien regarde alors autour de lui. Puis se rend compte que")));

    }

    @Test
	@SuppressWarnings("null")
    public void testCheckSessionAvailable1() throws Exception{
        String body = "{ \"session\": \"MysticalAshes\", \"password\": \"abcde\"}";

        mockMvc.perform(MockMvcRequestBuilders
                            .post("/session/validity")
                            .contentType("application/json")
                            .content(body))
                                .andExpect(status().is(200))
					            .andExpect(jsonPath("$.sessionAlreadyExisting", is(true)))
                                .andExpect(jsonPath("$.atLeastSixCharacters", is(false)))
                                .andExpect(jsonPath("$.includeLowercaseCharacters", is(true)))
                                .andExpect(jsonPath("$.includeUppercaseCharacters", is(false)))
                                .andExpect(jsonPath("$.includeDigits", is(false)))
                                .andExpect(jsonPath("$.creationPossible", is(false)));
    }

    @Test
	@SuppressWarnings("null")
    public void testCheckSessionAvailable2() throws Exception{
        String body = "{ \"session\": \"SleepingFox\", \"password\": \"Azur-7-Green\"}";

        mockMvc.perform(MockMvcRequestBuilders
                            .post("/session/validity")
                            .contentType("application/json")
                            .content(body))
                                .andExpect(status().is(200))
					            .andExpect(jsonPath("$.sessionAlreadyExisting", is(false)))
                                .andExpect(jsonPath("$.atLeastSixCharacters", is(true)))
                                .andExpect(jsonPath("$.includeLowercaseCharacters", is(true)))
                                .andExpect(jsonPath("$.includeUppercaseCharacters", is(true)))
                                .andExpect(jsonPath("$.includeDigits", is(true)))
                                .andExpect(jsonPath("$.creationPossible", is(true)));
    }

}
