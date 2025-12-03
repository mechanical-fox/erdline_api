package app.data.html;

public class ReferenceNode implements INode {

    private static int nextId = 1;
    private String id;
    private String css;
    private String ref;
    private String text;

    public ReferenceNode(AncreNode node, String css){
        this.id = "ref" + ReferenceNode.nextId;
        this.css = css;
        this.ref = node.getLabel();
        this.text = node.getText();

        ReferenceNode.nextId++;
    }

    public String getId(){
        return this.id;
    }

    /** A function than return the html text to represent this node */
    public String toHtml(){

        String result = "<a id =\"" + this.id + "\" class=\"" + this.css  + "\" + href=\"#" + this.ref + "\"> ";
        result += this.text + " </a>\n";
        
        return result;
    }
}
