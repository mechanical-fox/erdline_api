
package app.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;

import app.data.database.ExampleEntity;
import app.data.html.AncreNode;
import app.data.html.DivNode;
import app.data.html.ReferenceNode;
import app.data.in.Endpoint;
import app.data.in.ServerInfo;
import app.data.intern.Screen;
import app.data.intern.DocFile;
import app.data.intern.EndpointView;
import app.data.out.DocReponse;
import app.example.ExampleDocHtml;
import app.exception.BadRequestException;
import app.exception.NotFoundException;
import app.repository.ExampleRepository;
import app.util.Util;
import app.writer.Writer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@CrossOrigin(origins = {"http://localhost:4200", "https://erdline.fr","https://www.erdline.fr"})
@Tag(name = "Documentation")
@RestController
public class DocumentationController {

    @Autowired
    private ExampleRepository exampleRepository;

    private static final int EXPIRATION_TIME_SECOND = 180;
    private static final Map<String, DocFile> documents = new ConcurrentHashMap<String, DocFile>();

    /** Delete all the documents expired */
    private static void deleteExpired(){

        for(String name : documents.keySet()){
            if(DocumentationController.documents.get(name).isExpired())
                DocumentationController.documents.remove(name);
        }
    }

    /** Return the files known by the Controller, that aren't expired */
    public static Map<String, DocFile> getDocuments(){
        DocumentationController.deleteExpired();
        return DocumentationController.documents;
    }


    @Operation(summary = "Génération de documentation html")
    @ApiResponse(responseCode = "200", description = "Succès")
    @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content)
    @PostMapping(value="/documentation/html", produces="application/json")
    public DocReponse generateDocumentationHtml(@RequestBody ServerInfo infos) throws IOException, BadRequestException{

        DocumentationController.deleteExpired();
        String html = Writer.htmlTemplate();
        html = html.replace("<!-- :title -->", infos.getName());
        html = html.replace("<!-- :version -->", infos.getVersion());
        html = html.replace("<!-- :serveur -->", infos.getUrlServer());
        Map<String, List<Endpoint>> tagMap = Writer.getTagMap(infos.getEndpoints());
        DivNode node = new DivNode();
        List<ReferenceNode> references = new ArrayList<ReferenceNode>();
        List<Screen> activatedScreen = new ArrayList<Screen>();
        int id = 1;

        for(String tag : tagMap.keySet()){
            node = node.addDivChildren("margin2");
            AncreNode ancre = new AncreNode("ancre" + id, "text4", tag);
            references.add(new ReferenceNode(ancre, "nav_item_desactivated"));
            node.addExistingNode(ancre);
            id++;

            for(Endpoint endpoint : tagMap.get(tag)){
                EndpointView endpointView = Writer.viewEndpoint(endpoint, "endpoint_" + id);
                node.addExistingNode(endpointView.getNode());
                Screen screen = endpointView.getScreen();

                if(screen.getTabs().size() > 1)
                    activatedScreen.add(screen);
            }

            node = node.getParent();
        }

        html = Writer.writeSummary(html, references);
        html = Writer.writeScriptScreen(html, activatedScreen);
        String textEndpoint = Util.addIndent("\n" + node.toHtml(), 12);
        html = html.replace("<!-- :endpoints -->", textEndpoint);

        String htmlId = Util.generateUUID();
        DocFile document = new DocFile(htmlId, html, DocumentationController.EXPIRATION_TIME_SECOND);
        DocumentationController.documents.put(htmlId, document);
        int expirationTime = DocumentationController.EXPIRATION_TIME_SECOND;
        return new DocReponse(htmlId, "/access/" + htmlId, "/download/" + htmlId, expirationTime);
    }


    @Operation(summary = "Ouverture Onglet documentation html")
    @Parameter(name="file", example = "7a1fcbda8b63483e")
    @ApiResponse(responseCode = "200", description = "Succès", content=@Content(examples={@ExampleObject(value=ExampleDocHtml.htmlExample)}))
    @ApiResponse(responseCode = "404", description = "Fichier inexistant", content = @Content)
    @GetMapping(value = "/access/{file}", produces="text/html") 
    public String openDocumentation(@PathVariable String file) throws NotFoundException{

        DocumentationController.deleteExpired();
        DocFile document = DocumentationController.documents.get(file);

        if(document == null){
            List<ExampleEntity> examples = exampleRepository.queryByHtmlId(file);
        
            if(examples.size() == 0)
                throw new NotFoundException("Fichier inexistant, ou delai d'expiration depasse");
            else
                return examples.get(0).getHtml();
        }
        else
            return document.getHtml();
    }


    @Operation(summary = "Téléchargement documentation html")
    @Parameter(name="file", example = "7a1fcbda8b63483e")
    @ApiResponse(responseCode = "200", description = "Succès", content=@Content(examples={@ExampleObject(value=ExampleDocHtml.htmlExample)}))
    @ApiResponse(responseCode = "404", description = "Fichier inexistant", content = @Content)
    @GetMapping(value = "/download/{file}", produces="text/html")
    public HttpEntity<String> downloadDocumentation(@PathVariable String file) throws NotFoundException{

        DocumentationController.deleteExpired();
        DocFile document = DocumentationController.documents.get(file);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition","attachment"); //Header asking to download the file

        if(document == null){
            List<ExampleEntity> examples = exampleRepository.queryByHtmlId(file);
        
            if(examples.size() == 0)
                throw new NotFoundException("Fichier inexistant, ou delai d'expiration depasse");
            else
                return new HttpEntity<>(examples.get(0).getHtml(), headers);
        }
        else
            return new HttpEntity<>(document.getHtml(), headers);  
    }


}
