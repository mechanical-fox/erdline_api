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
    @Schema(example = "[{\"counter\" : 1, \"id\" : \"background-1\", \"name\" : \"Jardin\", \"color_id\" : \"color-3\"}+]")
    private String json_backgrounds;


    public SessionResponse(Long id, String session, Boolean isAdmin, String json_backgrounds){
        this.id = id;
        this.session = session;
        this.password = "******";
        this.isAdmin = isAdmin;
        this.json_backgrounds = json_backgrounds;
    }
}
