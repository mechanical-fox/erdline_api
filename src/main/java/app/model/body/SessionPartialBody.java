package app.model.body;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionPartialBody {

    @Schema(example = "[{\"counter\" : 1, \"id\" : \"background-1\", \"name\" : \"Jardin\", \"color_id\" : \"color-3\"}+]")
    private String json_backgrounds;

    public SessionPartialBody(){
        this.json_backgrounds = null;
    }

}
