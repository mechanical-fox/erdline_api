
package app.controller;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import app.model.response.HealthResponse;

@CrossOrigin
@Tag(name = "Observabilité")
@RestController
public class HealthController {


    @Operation(summary = "Vérifie l'état du serveur")
    @ApiResponse(responseCode = "200", description = "Succès")
    @GetMapping(value = "/health", produces="application/json")
    public HealthResponse health() throws ParserConfigurationException, SAXException, IOException{
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("pom.xml");
        NodeList nodes = document.getElementsByTagName("project");

        for(int i = 0; i < nodes.getLength();i++){
            NodeList candidates = nodes.item(i).getChildNodes();

            for(int u = 0; u  < candidates.getLength();u++){
                Node item = candidates.item(u);
                String nodeName = item.getNodeName();

                if("version".equals(nodeName)){
                    String version = item.getTextContent();
                    return new HealthResponse("Erdline", version , "Running");
                }
            }
        }

        return new HealthResponse("Erdline", null , "Running");
    }




}
