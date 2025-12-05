package app.data.intern;

import app.data.html.DivNode;
import lombok.Getter;

@Getter
public class EndpointView {
    
    private Screen screen;
    private DivNode node;

    public EndpointView(Screen screen, DivNode node){
        this.screen = screen;
        this.node = node;
    }
}
