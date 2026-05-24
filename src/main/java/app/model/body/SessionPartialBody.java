package app.model.body;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionPartialBody {

    @Schema(example = "[{\"counter\" : 1, \"id\" : \"background-1\", \"name\" : \"Jardin\", \"color_id\" : \"color-3\"},]")
    private String json_backgrounds;
    @Schema(example = "[{\"counter\" : 1, \"id\" : \"character-1\", \"name\": \"#1\", expressions: [{\"counter\" : 1, \"id\": " +
    "\"expr-1\", \"name\": \"Joie\", sprite_id : 2},]}]")
    private String json_characters;
    @Schema(example = "{\"createdAt\": 1779640052, \"counter\" : 1,\"id\" : \"scene-1\",\"name\" : \"Introduction\"," + 
    " \"backgroundId\"; : \"background-1\", \"messages\" : []}")
    private String json_scenes;

    public SessionPartialBody(){
        this.json_backgrounds = null;
        this.json_characters = null;
        this.json_scenes = null;
    }

}
