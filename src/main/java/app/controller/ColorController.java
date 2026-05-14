package app.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import app.model.body.ColorBody;
import app.model.database.ColorEntity;
import app.repository.ColorRepository;

@CrossOrigin
@Tag(name = "Couleurs")
@RestController
public class ColorController {

    @Autowired
    private ColorRepository colorRepository;

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
    public ColorEntity getColor(@PathVariable @NonNull Long id) throws NotFoundException{
        Optional<ColorEntity> color = colorRepository.findById(id);

        if(!color.isPresent())
            throw new NotFoundException("");

        return color.get();
    }

    
    @Operation(summary = "Création d'une nouvelle couleur")
    @ApiResponse(responseCode = "201", description = "Succès", content = @Content)
    @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content)
    @PostMapping(value="/color", produces = "text/plain")
    public ResponseEntity<String> postColor( @RequestBody ColorBody body) throws BadRequestException{
        if(body.getName() == null || body.getFirstGradient() == null || body.getSecondGradient() == null)
            throw new BadRequestException("The following fields are mandatory: name, firstGradient, secondGradient"); 

        ColorEntity color = new ColorEntity(body.getName(), body.getFirstGradient(), body.getSecondGradient());
        colorRepository.save(color);
        HttpHeaders responseHeaders = new HttpHeaders();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        responseHeaders.add("Location", baseUrl + "/color/" + color.getId());
        ResponseEntity<String> answer = new ResponseEntity<String>("",responseHeaders,201);
        return answer;
    }
}
