package app.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.helper.Helper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DocumentationControllerTests {

	@Autowired
    MockMvc mockMvc;

	@Test
	void generate_doc_returns_200_if_body_correct() throws Exception {
		String body = Helper.readAll("src/test/resources/body1.json");

		mockMvc.perform(MockMvcRequestBuilders
                        .post("/documentation/html")
                        .contentType("application/json")
                        .content(body))
                            .andExpect(status().is(200));
	}

}
