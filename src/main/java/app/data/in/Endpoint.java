
package app.data.in;

import java.util.List;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Endpoint {

    @Schema(description = "", example = "PUT")
    private String method;
    @Schema(description = "", example = "/config/gatling/{project}")
    private String path;
    @Schema(description = "", example = "Configuration")
    private String tag;
    private List<Parameter> parameters;
    @Schema(description = "", example = "[204, 404]")
    private List<Integer> status_list;
    @Schema(description = "")
    private List<Example> examples;

    public Endpoint(){
        this.method = null;
        this.path = null;
        this.tag = null;
        this.parameters = null;
        this.status_list = null;
        this.examples = null;
    }


}
