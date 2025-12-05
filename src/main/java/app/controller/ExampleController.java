
package app.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import app.data.database.ExampleEntity;
import app.data.intern.DocFile;
import app.data.out.ExampleResponse;
import app.exception.BadRequestException;
import app.exception.NotFoundException;
import app.exception.UnauthorizedException;
import app.repository.ExampleRepository;
import app.util.PasswordManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = {"http://localhost:4200", "https://erdline.fr"})
@Tag(name = "Exemple")
@RestController
public class ExampleController {

    @Autowired
    private ExampleRepository exampleRepository;

    @Operation(summary = "Sauvegarde une documentation comme exemple")
    @Parameter(name="htmlId", example = "e832cefda88b46b")
    @Parameter(name="name", example = "Documentation API Flopbox")
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = false, 
    description="Mot de passe nécessaire pour les opérations admin", example="azur-tech-47896")
    @ApiResponse(responseCode = "201", description = "Succès", content=@Content)
    @ApiResponse(responseCode = "400", description = "Requête Utilisateur Invalide", content=@Content)
    @ApiResponse(responseCode = "401", description = "Erreur d'authentification", content = @Content)
    @ApiResponse(responseCode = "404", description = "Fichier inexistant", content = @Content)
    @PostMapping(value = "/example/htmlId/{htmlId}/saveAs/{name}", produces={})
    public  ResponseEntity<String>  addExample(@PathVariable String htmlId, @PathVariable String name, 
    @RequestHeader HttpHeaders httpHeaders) throws NotFoundException, UnauthorizedException, BadRequestException{

        String password = PasswordManager.getErdlinePassword();
        List<String> authorizations = httpHeaders.get("Authorization");

        if(authorizations == null || authorizations.size() == 0)
            throw new UnauthorizedException("Header 'Authorization' inexistant");
        else if(!password.equals(authorizations.get(0)))
            throw new UnauthorizedException("Header 'Authorization' incorrect");

        Map<String, DocFile> documents = DocumentationController.getDocuments();

        if(documents == null || !documents.containsKey(htmlId))
            throw new NotFoundException("Fichier inexistant, ou delai d'expiration depasse");

        Iterable<ExampleEntity> examples = exampleRepository.findAll();

        for(ExampleEntity example : examples){
            if(example.getHtmlId() != null && example.getHtmlId().equals(htmlId)){
                String msg = "Fichier " + htmlId + " déjà sauvegardé en exemple sous le nom " + example.getName();
                throw new BadRequestException(msg);
            }
        }
        
        DocFile file = documents.get(htmlId);
        ExampleEntity example = new ExampleEntity(htmlId, name, file.getHtml());
        example = exampleRepository.save(example);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Location","/access/" + htmlId);
        ResponseEntity<String> entity = new ResponseEntity<>("", headers, 201);
        return entity;
    }


    @Operation(summary = "Supprime un exemple")
    @Parameter(name="htmlId", example = "e832cefda88b46b")
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = true, 
    description="Mot de passe nécessaire pour les opérations admin", example="azur-tech-47896")
    @ApiResponse(responseCode = "204", description = "Succès", content=@Content)
    @ApiResponse(responseCode = "401", description = "Erreur d'authentification", content = @Content)
    @ApiResponse(responseCode = "404", description = "Exemple inexistant", content = @Content)
    @DeleteMapping(value = "/example/{htmlId}", produces={})
    public ResponseEntity<String> deleteExample(@PathVariable String htmlId, 
    @RequestHeader HttpHeaders httpHeaders) throws NotFoundException, UnauthorizedException{

        String password = PasswordManager.getErdlinePassword();
        List<String> authorizations = httpHeaders.get("Authorization");

        if(authorizations == null || authorizations.size() == 0)
            throw new UnauthorizedException("Header 'Authorization' inexistant");
        else if(!password.equals(authorizations.get(0)))
            throw new UnauthorizedException("Header 'Authorization' incorrect");

        List<ExampleEntity> examples = exampleRepository.queryByHtmlId(htmlId);

        if(examples == null || examples.size() == 0)
            throw new NotFoundException("Exemple inexistant");
        
        for(ExampleEntity example : examples){
            if(example != null)
                exampleRepository.delete(example);
        }     

        return new ResponseEntity<String>("", new HttpHeaders(), 204);
    }



    @Operation(summary = "Liste les exemples existants")
    @ApiResponse(responseCode = "200", description = "Succès")
    @GetMapping(value = "/example", produces="application/json")
    public List<ExampleResponse> getExamples(){
        
        Iterable<ExampleEntity> examples = exampleRepository.findAll();
        List<ExampleResponse> result = new ArrayList<ExampleResponse>();
        
        for(ExampleEntity example : examples){
            String name = example.getName();
            String url_consultation = "/access/" + example.getHtmlId();
            String url_download = "/download/" + example.getHtmlId();
            ExampleResponse value = new ExampleResponse(example.getHtmlId(), name, url_consultation, url_download);
            result.add(value);
        } 
        
        return result;
    }




}
