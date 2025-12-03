package app.data.in;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerInfo {
    
    @Schema(description = "", example = "Erdline")
    private String name;
    @Schema(description = "", example = "1.10")
    private String version;
    @Schema(description = "", example = "https://127.0.0.1:8080")
    private String urlServer;
    private List<Endpoint> endpoints;

    public ServerInfo(){
        this.name = null;
        this.version = null;
        this.urlServer = null;
        this.endpoints = null;
    }
}
