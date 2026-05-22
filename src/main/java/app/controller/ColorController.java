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
import app.model.body.ColorBody;
import app.model.database.ColorEntity;
import app.model.database.SessionEntity;
import app.repository.ColorRepository;
import app.repository.TokenRepository;
import app.util.AuthUtil;

@CrossOrigin
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "Authorization", scheme = "bearer")
@Tag(name = "Couleurs")
@RestController
public class ColorController {

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Operation(summary = "Liste les couleurs disponibles")
    @ApiResponse(responseCode = "200", description = "Succès")
    @GetMapping(value = "/color", produces="application/json")
    public List<ColorEntity> listColors(){

        List<ColorEntity> colors = this.colorRepository.list();
        return colors;

    }

    @Operation(summary = "Recherche d'une couleur par Id")
    @Parameter(name="id", example = "1",required = true)
    @ApiResponse(responseCode = "200", description = "Succès")
    @ApiResponse(responseCode = "404", description = "Ressource Inexistante", content = @Content)
    @GetMapping(value="/color/{id}", produces="application/json")
    public ColorEntity getColorById(@PathVariable @NonNull Long id) throws NotFoundException{

        Optional<ColorEntity> color = colorRepository.findById(id);

        if(!color.isPresent())
            throw new NotFoundException("");

        return color.get();

    }

    
    @Operation(summary = "Création d'une nouvelle couleur")
    @SecurityRequirement(name = "Authorization")
    @ApiResponse(responseCode = "201", description = "Succès", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content)
    @ApiResponse(responseCode = "401", description = "Erreur d'authentification", content = @Content)
    @PostMapping(value="/color", produces = "text/plain")
    public ResponseEntity<String> postColor(@RequestHeader HttpHeaders headers, @RequestBody ColorBody body) 
    throws BadRequestException, UnauthorizedException{

        if(body.getName() == null || body.getFirstGradient() == null || body.getSecondGradient() == null)
            throw new BadRequestException("The following fields are mandatories: name, firstGradient, secondGradient"); 

        SessionEntity session = AuthUtil.identifySession(headers, tokenRepository);
        
        if(session == null)
            throw new UnauthorizedException("Erreur d'authentification");
        else if(!session.getIsAdmin())
            throw new UnauthorizedException("Utilisateur non administrateur");

        ColorEntity color = new ColorEntity(body.getName(), body.getFirstGradient(), body.getSecondGradient());
        colorRepository.save(color);
        HttpHeaders responseHeaders = new HttpHeaders();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        responseHeaders.add("Access-Control-Expose-Headers", "Location");
        responseHeaders.add("Location", baseUrl + "/color/" + color.getId());
        ResponseEntity<String> answer = new ResponseEntity<String>("",responseHeaders,201);
        return answer;

    }

    @Operation(summary = "Mise à jour d'une couleur par Id")
    @SecurityRequirement(name = "Authorization")
    @Parameter(name="id", example = "1",required = true)
    @ApiResponse(responseCode = "204", description = "Succès", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content)
    @ApiResponse(responseCode = "401", description = "Erreur d'authentification", content = @Content)
    @ApiResponse(responseCode = "404", description = "Ressource Inexistante", content = @Content)
    @PutMapping(value="/color/{id}", produces="text/plain")
    public ResponseEntity<String> putColor(@RequestHeader HttpHeaders headers, @PathVariable @NonNull Long id, 
    @RequestBody ColorBody body) throws BadRequestException, NotFoundException, UnauthorizedException{

        if(body.getName() == null || body.getFirstGradient() == null || body.getSecondGradient() == null)
            throw new BadRequestException("The following fields are mandatories: name, firstGradient, secondGradient"); 

        SessionEntity session = AuthUtil.identifySession(headers, tokenRepository);
        
        if(session == null)
            throw new UnauthorizedException("Erreur d'authentification");
        else if(!session.getIsAdmin())
            throw new UnauthorizedException("Utilisateur non administrateur");

        Optional<ColorEntity> color = colorRepository.findById(id);

        if(!color.isPresent())
            throw new NotFoundException("");

        ColorEntity entity = new ColorEntity(body.getName(), body.getFirstGradient(), body.getSecondGradient());
        entity.setId(id);
        colorRepository.save(entity);

        HttpHeaders responseHeaders = new HttpHeaders();
        ResponseEntity<String> answer = new ResponseEntity<String>("", responseHeaders, 204);
        return answer;

    }



}
