package app.data.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Example {

    
    @Schema(description = "", example = "body")
    private String name;
    @Schema(description = "", example="{\n\"injectionRandomized\": false,\n\"periods\": [\"duration\": 60,\"requestByMn\": 1500}]\n};")
    private String value;

    public Example(){
        this.name = null;
        this.value = null;
    }
    

}
