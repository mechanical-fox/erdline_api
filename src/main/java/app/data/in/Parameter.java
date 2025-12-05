
package app.data.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parameter {

    @Schema(description = "", example = "Path")
    private String type;
    @Schema(description = "", example = "project")
    private String name;
    @Schema(description = "", example = "CustomerWS")
    private String example;

    public Parameter(){
        this.type = null;
        this.name = null;
        this.example = null;
    }
    
}
