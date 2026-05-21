package app.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AuthResponse {
    
    @Schema(example = "1171660c4f41406d")
    private String token;
    @Schema(example = "3600")
    private Integer expireIn;
    @Schema(example = "Bearer Authentication")
    private String authorizationType;
    @Schema(example = "false")
    private Boolean isAdmin;

    public AuthResponse(String token, Integer expireIn, String authorizationType, Boolean isAdmin){
        this.token = token;
        this.expireIn = expireIn;
        this.authorizationType = authorizationType;
        this.isAdmin = isAdmin;
    }

}
