package app.data.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ExampleResponse {

    @Schema(description = "", example = "e832cefda88b46b")
    String htmlId;
    @Schema(description = "", example = "Documentation API Flopbox")
    String name;
    @Schema(description = "", example = "/access/e832cefda88b46b")
    String url_consultation;
    @Schema(description = "", example = "/download/e832cefda88b46b")
    String url_download;


    public ExampleResponse(String htmlId, String name, String url_consultation, String url_download){
        this.htmlId = htmlId;
        this.url_consultation = url_consultation;
        this.url_download = url_download;
        this.name = name;
    }
    
}
