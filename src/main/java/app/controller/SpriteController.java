package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

import app.exception.BadRequestException;
import app.exception.NotFoundException;
import app.exception.UnauthorizedException;
import app.model.body.SpriteBody;
import app.model.database.SessionEntity;
import app.model.database.SpriteEntity;
import app.repository.SpriteRepository;
import app.repository.TokenRepository;
import app.util.AuthUtil;


@CrossOrigin
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "Authorization", scheme = "bearer")
@Tag(name = "Sprites")
@RestController
public class SpriteController {

    @Autowired
    private SpriteRepository spriteRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Operation(summary = "Liste les sprites disponibles")
    @ApiResponse(responseCode = "200", description = "Succès")
    @GetMapping(value = "/sprite", produces="application/json")
    public List<SpriteEntity> listSprites(){

        List<SpriteEntity> colors = this.spriteRepository.list();
        return colors;

    }

    @Operation(summary = "Recherche d'un sprite par Id")
    @Parameter(name="id", example = "1",required = true)
    @ApiResponse(responseCode = "200", description = "Succès")
    @ApiResponse(responseCode = "404", description = "Ressource Inexistante", content = @Content)
    @GetMapping(value="/sprite/{id}", produces="application/json")
    public SpriteEntity getSpriteById(@PathVariable @NonNull Long id) throws NotFoundException{

        Optional<SpriteEntity> sprite = spriteRepository.findById(id);

        if(!sprite.isPresent())
            throw new NotFoundException("");

        return sprite.get();

    }



    @Operation(summary = "Création d'un nouveau sprite")
    @SecurityRequirement(name = "Authorization")
    @ApiResponse(responseCode = "201", description = "Succès", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content)
    @ApiResponse(responseCode = "401", description = "Erreur d'authentification", content = @Content)
    @PostMapping(value="/sprite", produces = "text/plain")
    public ResponseEntity<String> createSprite(@RequestHeader HttpHeaders headers, @RequestBody SpriteBody body) 
    throws BadRequestException, UnauthorizedException{

        if(body.getName() == null || body.getData() == null)
            throw new BadRequestException("The following fields are mandatories: name, data"); 

        SessionEntity session = AuthUtil.identifySession(headers, tokenRepository);
        
        if(session == null)
            throw new UnauthorizedException("Erreur d'authentification");
        else if(!session.getIsAdmin())
            throw new UnauthorizedException("Utilisateur non administrateur");

        SpriteEntity sprite = new SpriteEntity(body.getName(), body.getFilename(), body.getData());
        spriteRepository.save(sprite);
        HttpHeaders responseHeaders = new HttpHeaders();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        responseHeaders.add("Access-Control-Expose-Headers", "Location");
        responseHeaders.add("Location", baseUrl + "/sprite/" + sprite.getId());
        ResponseEntity<String> answer = new ResponseEntity<String>("",responseHeaders,201);
        return answer;

    }


    @Operation(summary = "Mise à jour d'un sprite par Id")
    @SecurityRequirement(name = "Authorization")
    @Parameter(name="id", example = "1",required = true)
    @ApiResponse(responseCode = "204", description = "Succès", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content)
    @ApiResponse(responseCode = "401", description = "Erreur d'authentification", content = @Content)
    @ApiResponse(responseCode = "404", description = "Ressource Inexistante", content = @Content)
    @PutMapping(value="/sprite/{id}", produces="text/plain")
    public ResponseEntity<String> modifySprite(@RequestHeader HttpHeaders headers, @PathVariable @NonNull Long id, 
    @RequestBody SpriteBody body) throws BadRequestException, UnauthorizedException, NotFoundException{

        if(body.getName() == null || body.getData() == null)
            throw new BadRequestException("The following fields are mandatories: name, data"); 

        SessionEntity session = AuthUtil.identifySession(headers, tokenRepository);
        
        if(session == null)
            throw new UnauthorizedException("Erreur d'authentification");
        else if(!session.getIsAdmin())
            throw new UnauthorizedException("Utilisateur non administrateur");

        Optional<SpriteEntity> sprite = spriteRepository.findById(id);

        if(!sprite.isPresent())
            throw new NotFoundException("");

        SpriteEntity entity = new SpriteEntity(body.getName(), body.getFilename(), body.getData());
        entity.setId(id);
        spriteRepository.save(entity);

        HttpHeaders responseHeaders = new HttpHeaders();
        ResponseEntity<String> answer = new ResponseEntity<String>("", responseHeaders, 204);
        return answer;

    }




}
