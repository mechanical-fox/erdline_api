package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import app.exception.BadRequestException;
import app.exception.NotFoundException;
import app.exception.UnauthorizedException;
import app.model.body.SessionBody;
import app.model.body.SessionCheckBody;
import app.model.body.SessionPartialBody;
import app.model.database.SessionEntity;
import app.model.database.TokenEntity;
import app.model.response.AuthResponse;
import app.model.response.SessionResponse;
import app.model.response.VerificationResponse;
import app.repository.SessionRepository;
import app.repository.TokenRepository;
import app.util.AuthUtil;
import app.util.Util;

@CrossOrigin
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "Authorization", scheme = "bearer")
@Tag(name = "Session")
@RestController
public class SessionController {

    private static final int TOKEN_DURATION_SECOND = 3600;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Operation(summary = "Création d'une nouvelle session")
    @ApiResponse(responseCode = "201", description = "Succès", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content)
    @PostMapping(value="/session", produces = "text/plain")
    public ResponseEntity<String> createSession( @RequestBody SessionBody body) throws BadRequestException, NoSuchAlgorithmException{

        if(body.getSession() == null || body.getPassword() == null)
            throw new BadRequestException("The following fields are mandatories: session, password"); 

        List<SessionEntity> sessions = this.sessionRepository.searchBySession(body.getSession());

        if(sessions.size() > 0)
            throw new BadRequestException("This session name is already used");
        if(body.getSession().length() < 4)
            throw new BadRequestException("Password must be at least 6 characters long");
        if(body.getPassword().toLowerCase().equals(body.getPassword()))
            throw new BadRequestException("Password must contains at least one Uppercase Character");
        if(body.getPassword().toUpperCase().equals(body.getPassword()))
            throw new BadRequestException("Password must contains at least one Lowercase Character");
        if(!Util.includeDigit(body.getPassword()))
            throw new BadRequestException("Password must contains at least one digit");

        String hash_password = Util.hash(body.getPassword());
        SessionEntity entity = new SessionEntity(body.getSession(), hash_password, false, body.getJson_backgrounds(),
            body.getJson_characters(), body.getJson_scenes());
        this.sessionRepository.save(entity);
        
        HttpHeaders responseHeaders = new HttpHeaders();
        ResponseEntity<String> answer = new ResponseEntity<String>("",responseHeaders,201);
        return answer;

    }

    @Operation(summary = "Recherche d'une session par id")
    @SecurityRequirement(name = "Authorization")
    @Parameter(name="id", example = "1",required = true)
    @ApiResponse(responseCode = "200", description = "Succès")
    @ApiResponse(responseCode = "401", description = "Erreur d'authentification", content = @Content)
    @ApiResponse(responseCode = "404", description = "Ressource Inexistante", content = @Content)
    @GetMapping(value="/session/{id}", produces = "application/json")
    public SessionResponse getSession(@RequestHeader HttpHeaders headers, @PathVariable @NonNull Long id) 
    throws NotFoundException, UnauthorizedException{

        Optional<SessionEntity> session = this.sessionRepository.findById(id);

        if(!session.isPresent())
            throw new NotFoundException("");

        SessionEntity sessionByToken = AuthUtil.identifySession(headers, tokenRepository);

        if(sessionByToken == null || sessionByToken.getId() != id)
            throw new UnauthorizedException("");

        Long idResponse = sessionByToken.getId();
        String sessionName = sessionByToken.getSession();
        Boolean isAdmin = sessionByToken.getIsAdmin();
        String json_backgrounds = sessionByToken.getJson_backgrounds();
        String json_characters = sessionByToken.getJson_characters();
        String json_scenes = sessionByToken.getJson_scenes();
        SessionResponse response = new SessionResponse(idResponse, sessionName,isAdmin, json_backgrounds, json_characters, json_scenes);
        return response;
    }

    @Operation(summary = "Modification partielle d'une session par id")
    @SecurityRequirement(name = "Authorization")
    @Parameter(name="id", example = "1",required = true)
    @ApiResponse(responseCode = "204", description = "Succès", content = @Content)
    @ApiResponse(responseCode = "401", description = "Erreur d'authentification", content = @Content)
    @ApiResponse(responseCode = "404", description = "Ressource Inexistante", content = @Content)
    @PatchMapping(value="/session/{id}", produces = "text/plain")
    public ResponseEntity<String> patchSession(@RequestHeader HttpHeaders headers, @PathVariable @NonNull Long id,
    @RequestBody SessionPartialBody body) 
    throws NotFoundException, UnauthorizedException{

        Optional<SessionEntity> session = this.sessionRepository.findById(id);

        if(!session.isPresent())
            throw new NotFoundException("");

        SessionEntity sessionByToken = AuthUtil.identifySession(headers, tokenRepository);

        if(sessionByToken == null || sessionByToken.getId() != id)
            throw new UnauthorizedException("");

        if(body.getJson_backgrounds() != null)
            sessionByToken.setJson_backgrounds(body.getJson_backgrounds());
        if(body.getJson_characters() != null)
            sessionByToken.setJson_characters(body.getJson_characters());
        if(body.getJson_scenes() != null)
            sessionByToken.setJson_scenes(body.getJson_scenes());

        this.sessionRepository.save(sessionByToken);

        HttpHeaders responseHeaders = new HttpHeaders();
        ResponseEntity<String> answer = new ResponseEntity<String>("",responseHeaders,204);
        return answer;
    }
    

    @Operation(summary = "Vérifie si la création d'une session est possible")
    @ApiResponse(responseCode = "200", description = "Succès")
    @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content)
    @PostMapping(value="/session/validity", produces = "application/json")
    public VerificationResponse checkSessionAvailable( @RequestBody SessionCheckBody body) throws BadRequestException{

        if(body.getSession() == null || body.getPassword() == null)
            throw new BadRequestException("The following fields are mandatories: session, password"); 

        List<SessionEntity> sessions = this.sessionRepository.searchBySession(body.getSession());
        boolean sessionAlreadyExisting = sessions.size() > 0;
        boolean atLeastSixCharacters = body.getPassword().length() >= 6;
        boolean includeLowercaseCharacters = !body.getPassword().toUpperCase().equals(body.getPassword());
        boolean includeUppercaseCharacters = !body.getPassword().toLowerCase().equals(body.getPassword());
        boolean includeDigits = Util.includeDigit(body.getPassword());

        boolean isCreationPossible = !sessionAlreadyExisting && atLeastSixCharacters && includeLowercaseCharacters;
        isCreationPossible = isCreationPossible && includeUppercaseCharacters && includeDigits;

        VerificationResponse response = new VerificationResponse(isCreationPossible, sessionAlreadyExisting, atLeastSixCharacters, 
            includeLowercaseCharacters, includeUppercaseCharacters, includeDigits);

        return response;

    }


    @Operation(summary = "Demande d'un token d'authentification")
    @ApiResponse(responseCode = "200", description = "Succès")
    @ApiResponse(responseCode = "401", description = "Erreur d'authentification", content=@Content)
    @PostMapping(value="/auth", produces="application/json")
    public AuthResponse requestToken(@RequestBody SessionCheckBody body) throws UnauthorizedException, NoSuchAlgorithmException{

        if(body.getSession() == null || body.getPassword() == null)
            throw new UnauthorizedException("The following fields are mandatories: session, password");

        List<SessionEntity> sessions = sessionRepository.searchBySession(body.getSession());

        if(sessions.size() == 0)
            throw new UnauthorizedException("Session not Found");

        SessionEntity session = sessions.get(0);
        String hashPassword = Util.hash(body.getPassword());
        
        if(!hashPassword.equals(session.getHash_password()))
            throw new UnauthorizedException("Password incorrect");

        String token = Util.generateToken();
        OffsetDateTime expirationDate = OffsetDateTime.now().plusSeconds(SessionController.TOKEN_DURATION_SECOND);
        TokenEntity tokenEntity = new TokenEntity(token, session, expirationDate);
        this.tokenRepository.save(tokenEntity);
        this.tokenRepository.deleteExpiredTokens();
        return new AuthResponse(token, SessionController.TOKEN_DURATION_SECOND, "Bearer Authentication", session.getId(), session.getIsAdmin());
    }


}
