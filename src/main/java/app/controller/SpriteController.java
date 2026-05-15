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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;
import java.util.Optional;

import app.exception.BadRequestException;
import app.exception.NotFoundException;
import app.model.body.SpriteBody;
import app.model.database.SpriteEntity;
import app.repository.SpriteRepository;


@CrossOrigin
@Tag(name = "Sprites")
@RestController
public class SpriteController {

    @Autowired
    private SpriteRepository spriteRepository;

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
    @ApiResponse(responseCode = "201", description = "Succès", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content)
    @PostMapping(value="/sprite", produces = "text/plain")
    public ResponseEntity<String> postSprite( @RequestBody SpriteBody body) throws BadRequestException{

        if(body.getName() == null || body.getData() == null)
            throw new BadRequestException("The following fields are mandatories: name, data"); 

        SpriteEntity sprite = new SpriteEntity(body.getName(), body.getData());
        spriteRepository.save(sprite);
        HttpHeaders responseHeaders = new HttpHeaders();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        responseHeaders.add("Access-Control-Expose-Headers", "Location");
        responseHeaders.add("Location", baseUrl + "/sprite/" + sprite.getId());
        ResponseEntity<String> answer = new ResponseEntity<String>("",responseHeaders,201);
        return answer;

    }


    @Operation(summary = "Mise à jour d'un sprite par Id")
    @Parameter(name="id", example = "1",required = true)
    @ApiResponse(responseCode = "204", description = "Succès", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content)
    @ApiResponse(responseCode = "404", description = "Ressource Inexistante", content = @Content)
    @PutMapping(value="/sprite/{id}", produces="text/plain")
    public ResponseEntity<String> putSprite(@PathVariable @NonNull Long id, @RequestBody SpriteBody body) throws BadRequestException, NotFoundException{

        if(body.getName() == null || body.getData() == null)
            throw new BadRequestException("The following fields are mandatories: name, data"); 

        Optional<SpriteEntity> sprite = spriteRepository.findById(id);

        if(!sprite.isPresent())
            throw new NotFoundException("");

        SpriteEntity entity = new SpriteEntity(body.getName(), body.getData());
        entity.setId(id);
        spriteRepository.save(entity);

        HttpHeaders responseHeaders = new HttpHeaders();
        ResponseEntity<String> answer = new ResponseEntity<String>("", responseHeaders, 204);
        return answer;

    }




}
