package app.data.html;

public class TextNode implements INode{
    

    private String id;
    private String css;
    private String text;

    public TextNode(String css, String text){
        this.id = null;
        this.css = css;
        this.text = text;
    }

    public TextNode(String id, String css, String text){
        this.id = id;
        this.css = css;
        this.text = text;
    }


    /** A function than return the html text to represent this node */
    public String toHtml(){

        String result = "<p";
        
        if(this.id != null)
            result += " id=\"" + this.id + "\"";
        
        result += " class=\"" + this.css + "\"";
        result += "> " + this.text + " </p>\n";
        return result;
    }
}
