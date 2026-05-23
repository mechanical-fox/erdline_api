package app.model.body;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionBody {
    
    @Schema(example = "MysticalAshes")
    private String session;
    @Schema(example = "sKyrIm-4678")
    private String password;
    @Schema(example = "[{\"counter\" : 1, \"id\" : \"background-1\", \"name\" : \"Jardin\", \"color_id\" : \"color-3\"},]")
    private String json_backgrounds;
    @Schema(example = "[{\"counter\" : 1, \"id\" : \"character-1\", \"name\": \"#1\", expressions: [{\"counter\" : 1, \"id\": " +
    "\"expr-1\", \"name\": \"Joie\", sprite_id : 2},{\"counter\" : 2, \"id\" : \"expr-2\", \"name\": \"\", sprite_id : null},]}]")
    private String json_characters;


    public SessionBody(){
        this.session = null;
        this.password = null;
        this.json_backgrounds = null;
        this.json_characters = null;
    }
}
