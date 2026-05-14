package app.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Getter
public class HealthResponse {

    @Schema(example = "Erdline")
    private String api;
    @Schema(example = "1.1")
    private String version;
    @Schema(example = "Running")
    private String status;

    public HealthResponse(String api, String version, String status){
        this.api = api;
        this.version = version;
        this.status = status;
    }
    
}
