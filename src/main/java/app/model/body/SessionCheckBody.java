package app.model.body;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionCheckBody {
    
    @Schema(example = "MysticalAshes")
    private String session;
    @Schema(example = "sKyrIm-4678")
    private String password;

    public SessionCheckBody(){
        this.session = null;
        this.password = null;
    }

}
