package app.data.html;

import java.util.ArrayList;
import java.util.List;

import app.util.Util;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DivNode implements INode{

    private DivNode parent;
    private String css;
    private List<INode> childs;

    public DivNode(){
        this.css = null;
        this.childs = new ArrayList<INode>();
        this.parent = null;
    }
    
    public DivNode(String css){
        this.css = css;
        this.childs = new ArrayList<INode>();
        this.parent = null;
    }

    /** Add for children a DivNode alreading existing */
    public void addExistingNode(DivNode children){
        children.setParent(this);
        this.childs.add(children);
    }

    /** Add for children a INode alreading existing */
    public void addExistingNode(INode children){
        this.childs.add(children);
    }

    /** Add for children a balise <div>, and return the children. */
    public DivNode addDivChildren(){
        DivNode children = new DivNode();
        children.setParent(this);
        this.childs.add(children);
        return children;
    }

    /** Add for children a balise <div>, with a css class, and return the children. */
    public DivNode addDivChildren(String css){
        DivNode children = new DivNode(css);
        children.setParent(this);
        this.childs.add(children);
        return children;
    }

    /** Add for children a balise <p> with an id*/
    public void addTextChildren(String css, String text){
        INode children = new TextNode(css, text);
        this.childs.add(children);
    }

    /** Add for children a balise <p> without id*/
    public void addTextChildren(String id, String css, String text){
        INode children = new TextNode(id, css, text);
        this.childs.add(children);
    }

    /** Add for children a balise <p> with a css class*/
    public void addAncreChildren(String label, String css, String text){
        INode children = new AncreNode(label,css, text);
        this.childs.add(children);
    }

    /** Return the parent, or null */
    public DivNode getParent(){
        return this.parent;
    }

    /** Return the Root, so the node containing all the other nodes. */
    public DivNode getRoot(){
        DivNode previous = null, current = this;

        while(current != null){
            previous = current;
            current = current.getParent();
        }

        return previous;
    }


    /** A function than return the html text to represent this node. */
    public String toHtml(){
        String result = "<div";

        if(this.css != null)
            result += " class=\"" + this.css + "\"";
        
        result += ">\n";

        for(INode child : this.childs)
            result += Util.addIndent(child.toHtml(), 4);

        result += "</div>\n";
        return result;
    }

}
