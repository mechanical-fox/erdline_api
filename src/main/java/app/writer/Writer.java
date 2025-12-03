package app.writer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.exception.BadRequestException;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.data.html.DivNode;
import app.data.html.ReferenceNode;
import app.data.in.Endpoint;
import app.data.in.Example;
import app.data.in.Parameter;
import app.data.intern.EndpointView;
import app.data.intern.Screen;
import app.data.intern.Tab;
import app.util.Util;

public class Writer {
    
    private static final String HTML_FILE = "src/main/resources/template/template.html";
    private static final String CSS_FILE = "src/main/resources/template/template.css";
    private static final String JS_FILE = "src/main/resources/template/template.js";

    private static String extendedTemplate = null;


    /** Return a map associating each tag, with the list of the endpoints matching this tag. A tag, is a 
    * group used to group together many endpoint. Each endpoint have exactly one tag.*/
    public static Map<String, List<Endpoint>> getTagMap(List<Endpoint> endpoints){

        Map<String, List<Endpoint>> tagMap = new HashMap<String, List<Endpoint>>();

        for(Endpoint endpoint: endpoints){
            String tag = endpoint.getTag(); 
            List<Endpoint> groupedEndpoints = tagMap.get(tag);

            if(groupedEndpoints == null)
                groupedEndpoints = new ArrayList<Endpoint>();

            groupedEndpoints.add(endpoint);
            tagMap.put(tag, groupedEndpoints);
        }

        return tagMap;
    }


    /** Return the template to use, for generate a documentation.*/
    public static String htmlTemplate() throws IOException{

        if(extendedTemplate != null)
            return extendedTemplate;

        String html = Util.readAll(Writer.HTML_FILE);
        String css = Util.readAll(Writer.CSS_FILE);
        String js = Util.readAll(Writer.JS_FILE);
        css = Util.addIndent(css, 12);
        css = "\n        <style>\n" + css + "\n        </style>";
        html = html.replace("<!-- :css -->",css);
        js = Util.addIndent(js, 12);
        js = "\n        <script>\n" + js + "\n        </script>";
        html = html.replace("<!-- :script -->",js);

        Writer.extendedTemplate = html;
        return html;
    }


    /** Given a html template, and a list of Screen to activated. Return the html template with the javascript code 
    * necessary to activate the screens, writted. What is referred as a screen, are the objects with many tabs  
    * with the title "Exemples" in the documentation generated. */
    public static String writeScriptScreen(String html, List<Screen> activatedScreen) throws IOException{

        ObjectMapper objectMapper = new ObjectMapper();
        String code2 = "screens = " + objectMapper.writeValueAsString(activatedScreen) + ";";
        html = html.replace("/* :screens */", code2);

        return html;
    }


    /** Return a representation by node html of the examples of the endpoint. */
    public static DivNode writeEndpointExamples(Screen screen){
        DivNode node = new DivNode("indented");
        node.addTextChildren("text2", "<b>Exemples</b>");

        if(screen.getTabs().size() == 0){
            node = node.addDivChildren("indented");
            node.addTextChildren("text1", " Endpoint ne renvoyant pas de résultats ");
            node = node.getParent();
        }
        else{
            node = node.addDivChildren("margin4 indented");
            List<Tab> tabs = screen.getTabs();

            for(int i = 0; i < tabs.size();i++){

                if(i == 0)
                    node.addTextChildren(tabs.get(i).getId(), "tab_activated", tabs.get(i).getName());
                else
                    node.addTextChildren(tabs.get(i).getId(), "tab_desactivated", tabs.get(i).getName());
            }

            node = node.addDivChildren("tab_div");
            node.addTextChildren(screen.getView_id(), "tab_text", screen.getFirstDisplayed());
            node = node.getParent();
            node = node.getParent();
        }

        return node;
    }


    /** Given a html template, and a list of References, write the javascript and html code necessary to create the summary. And return
    *  the html template with the missing code writted. The javascript code will be updated in the balise <script>. */
    public static String writeSummary(String html, List<ReferenceNode> references){

        String code1 = "nav_items = [";
        
        for(ReferenceNode ref : references)
            code1 += "\"" + ref.getId() + "\",";
        
        if(references.size() > 0)
            code1 = code1.substring(0, code1.length() - 1);
        
        code1 += "];";
        html = html.replace("/* :nav-items-js */", code1);
        DivNode nodeNav = new DivNode("nav");
        
        for(ReferenceNode ref : references)
            nodeNav.addExistingNode(ref);

        String textNav = Util.addIndent("\n" + nodeNav.toHtml(), 8);
        html = html.replace("<!-- :nav-items -->", textNav);
        return html;
    }


    /** Return a representation of the endpoint with two objets, an html node, and a screen. The screen is a object than
     * will help to program how switch the tabs, if there is many examples */
    public static EndpointView viewEndpoint(Endpoint endpoint, String id) throws BadRequestException{
        DivNode node = new DivNode();
        node = node.addDivChildren("margin3");
        node = node.addDivChildren();
        node.addTextChildren(Writer.getMethodColor(endpoint) + " method_badge", endpoint.getMethod());
        node.addTextChildren("text2 demi_indented inline", endpoint.getPath());
        node = node.addDivChildren("line");
        node = node.addDivChildren("column_left max_width_1");
        node.addExistingNode(Writer.nodeEndpointParameters(endpoint));
        node.addExistingNode(Writer.nodeEndpointStatus(endpoint));
        node = node.getParent();
        node = node.addDivChildren("column_left");
        Screen screen = Writer.getEndpointScreen(endpoint, id);
        node.addExistingNode(Writer.writeEndpointExamples(screen));
        node = node.getRoot();

        return new EndpointView(screen, node);
    }


    /** Return the object Screen, associated to represent the exemples */
    private static Screen getEndpointScreen(Endpoint endpoint, String id){
        List<Example> examples = endpoint.getExamples();
        String view_id = id + "_" + (examples.size() + 1);
        Screen result = new Screen(view_id);

        for(int i = 0; i < examples.size();i++){
            String subId = id + "_" + (i + 1);
            String name =  examples.get(i).getName();
            String valueHtml = examples.get(i).getValue();
            result.addTab(subId, name, valueHtml);
        }
        
        return result;
    }
   
    
    /** Depending of the method of an endpoint (GET, POST, PUT...) return the color to use for the method badge of this 
     * endpoint. The color answered is a css class, like by example "bgreen". */
    private static String getMethodColor(Endpoint endpoint){
        if(endpoint.getMethod().equals("GET"))
            return "bgreen";
        else if(endpoint.getMethod().equals("POST"))
            return "bblue";
        else if(endpoint.getMethod().equals("PATCH"))
            return "blightblue";
        else if(endpoint.getMethod().equals("PUT"))
            return "borange";
        else if(endpoint.getMethod().equals("DELETE"))
            return "bred";
        else
            return "bpurple";
    }


    /** Return a representation by node html of the parameters of the endpoint.*/
    private static DivNode nodeEndpointParameters(Endpoint endpoint){
        DivNode node = new DivNode("indented");

        node.addTextChildren("text2", "<b>Paramètres</b>");

        if(endpoint.getParameters().size() == 0){
            node = node.addDivChildren("indented");
            node.addTextChildren("text1", " Aucun paramètres requis");
            node = node.getParent();
        }
        
        for(Parameter parameter : endpoint.getParameters()){
            node = node.addDivChildren("indented");
            node.addTextChildren("text1", parameter.getName() + " (" + parameter.getType() +  ")");
            node.addTextChildren("text3 indented", "<i>Exemple: " + parameter.getExample() + " </i>");
            node = node.getParent();
        }

        return node;
    }


    /** Return a representation by node html of the status code of the endpoint. */
    private static DivNode nodeEndpointStatus(Endpoint endpoint) throws BadRequestException{
        DivNode node = new DivNode("indented");
        node.addTextChildren("text2", "<b>Codes de retour</b>");
        node = node.addDivChildren("indented");

        if(endpoint.getStatus_list().size() == 0)
            throw new BadRequestException("Au moins un code de retour doit être renseigné");
        
        for(Integer status : endpoint.getStatus_list()){

            if(status != null && status < 300)
                node.addTextChildren("status_badge bgreen", "" + status);
            else
                node.addTextChildren("status_badge bred", "" + status);
        }

        return node.getParent();
    }
}
