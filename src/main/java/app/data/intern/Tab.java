package app.data.intern;

import app.util.Util;
import lombok.Getter;

@Getter
public class Tab {
    
    private String id;
    private String name;
    private String content;

    public Tab(String id, String name, String content){
        this.id = id;
        this.name = name;
        this.content = Util.toHtmlEscaped(content);
    }
}
