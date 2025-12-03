
package app.data.intern;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Screen{

    private String view_id;
    private List<Tab> tabs;

    public Screen(String view_id){
        this.view_id = view_id;
        this.tabs = new ArrayList<Tab>();
    }

    /** Add a new tab at a screen. */
    public void addTab(String id, String name, String value){
        Tab t = new Tab(id, name, value);
        this.tabs.add(t);
    }

    /** Return the text of the first tab to display */
    public String getFirstDisplayed(){
        if(this.tabs.size() == 0)
            return null;
        else
            return this.tabs.get(0).getContent();
    }

}