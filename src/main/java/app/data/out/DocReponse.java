package app.data.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class DocReponse {
    
    @Schema(description = "", example = "7a1fcbda8b63483e")
    String htmlId;
    @Schema(description = "", example = "/access/7a1fcbda8b63483e")
    String url_consultation;
    @Schema(description = "", example = "/download/7a1fcbda8b63483e")
    String url_download;
    @Schema(description = "", example = "180")
    int expireIn;

    public DocReponse(String htmlId, String url_consultation, String url_download, int expireIn){
        this.htmlId = htmlId;
        this.url_consultation = url_consultation;
        this.url_download = url_download;
        this.expireIn = expireIn;
    }
    
}
