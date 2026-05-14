package app.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Getter
public class HealthResponse {

    @Schema(example = "1171660c4f41406d")
    private String API;
    @Schema(example = "1.1")
    private String version;
    @Schema(example = "Running")
    private String status;

    public HealthResponse(String API, String version, String status){
        this.API = API;
        this.version = version;
        this.status = status;
    }
    
}
