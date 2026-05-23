package app.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionResponse {
    
    @Schema(example = "1")
    private Long id;
    @Schema(example = "MysticalAshes")
    private String session;
    @Schema(example = "******")
    private String password;
    @Schema(example = "true")
    private Boolean isAdmin;
    @Schema(example = "[{\"counter\" : 1, \"id\" : \"background-1\", \"name\" : \"Jardin\", \"color_id\" : \"color-3\"},]")
    private String json_backgrounds;
    @Schema(example = "[{\"counter\" : 1, \"id\" : \"character-1\", \"name\": \"#1\", expressions: [{\"counter\" : 1, \"id\": " +
    "\"expr-1\", \"name\": \"Joie\", sprite_id : 2},]}]")
    private String json_characters;


    public SessionResponse(Long id, String session, Boolean isAdmin, String json_backgrounds, String json_characters){
        this.id = id;
        this.session = session;
        this.password = "******";
        this.isAdmin = isAdmin;
        this.json_backgrounds = json_backgrounds;
        this.json_characters = json_characters;
    }
}
