package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;
import java.util.Optional;

import app.exception.NotFoundException;
import app.exception.UnauthorizedException;
import app.model.database.ExampleEntity;
import app.model.database.SessionEntity;
import app.model.response.ExampleResponse;
import app.repository.ExampleRepository;
import app.repository.SessionRepository;
import app.repository.TokenRepository;
import app.util.AuthUtil;


@CrossOrigin
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "Authorization", scheme = "bearer")
@Tag(name = "Exemple")
@RestController
public class ExampleController {
    
    @Autowired
    private ExampleRepository exampleRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private SessionRepository sessionRepository;


    @Operation(summary = "Retourne un exemple de Visual Novel")
    @ApiResponse(responseCode = "200", description = "Succès")
    @GetMapping(value = "/example", produces="application/json")
    public ExampleResponse getExample(){

        List<ExampleEntity> examples = this.exampleRepository.list();

        if(examples.size() > 0){
            ExampleEntity example = examples.get(0);
            return new ExampleResponse(example.getJson_backgrounds(), example.getJson_characters(), example.getJson_scenes());
        }
        else
            return new ExampleResponse();
            
    }

    
    @Operation(summary = "Choisit le Visual Novel d'une session comme exemple")
    @SecurityRequirement(name = "Authorization")
    @Parameter(name="id", example = "1",required = true)
    @ApiResponse(responseCode = "204", description = "Succès", content = @Content)
    @ApiResponse(responseCode = "401", description = "Erreur d'authentification", content = @Content)
    @ApiResponse(responseCode = "404", description = "Session Inexistante", content = @Content)
    @PutMapping(value="/example/push_from_session/{id}", produces = "text/plain")
    public ResponseEntity<String> putExampleFromSession(@RequestHeader HttpHeaders headers, @RequestParam @NonNull Long id) 
    throws UnauthorizedException, NotFoundException{

        Optional<SessionEntity> session = sessionRepository.findById(id);

        if(!session.isPresent())
            throw new NotFoundException("");

        SessionEntity sessionByToken = AuthUtil.identifySession(headers, tokenRepository);
        
        if(sessionByToken == null || sessionByToken.getId() != id)
            throw new UnauthorizedException("");

        String json_backgrounds = sessionByToken.getJson_backgrounds();
        String json_characters = sessionByToken.getJson_characters();
        String json_scenes = sessionByToken.getJson_scenes();
        ExampleEntity example = new ExampleEntity(json_backgrounds, json_characters, json_scenes);
        exampleRepository.deleteAll();
        exampleRepository.save(example);

        HttpHeaders responseHeaders = new HttpHeaders();
        ResponseEntity<String> answer = new ResponseEntity<String>("",responseHeaders,204);
        return answer;
    }


}
