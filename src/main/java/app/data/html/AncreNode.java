package app.data.html;

import lombok.Getter;

@Getter
public class AncreNode implements INode {
    
    private String label;
    private String css;
    private String text;

    public AncreNode(String label, String css, String text){
        this.label = label;
        this.css = css;
        this.text = text;
    }

    /** A function than return the html text to represent this node */
    public String toHtml(){
        return "<a name =\"" + this.label + "\" class=\"" + this.css + "\">" + this.text + "</a>\n";
    }
}
