package app.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class VerificationResponse {
    
    @Schema(example = "true")
    private boolean isCreationPossible;
    @Schema(example = "false")
    private boolean sessionAlreadyExisting;
    @Schema(example = "true")
    private boolean atLeastSixCharacters;
    @Schema(example = "true")
    private boolean includeLowercaseCharacters;
    @Schema(example = "true")
    private boolean includeUppercaseCharacters;
    @Schema(example = "true")
    private boolean includeDigits;

    public VerificationResponse(boolean isCreationPossible, boolean sessionAlreadyExisting, boolean atLeastSixCharacters, 
    boolean includeLowercaseCharacters, boolean includeUppercaseCharacters, boolean includeDigits){

        this.isCreationPossible = isCreationPossible;
        this.sessionAlreadyExisting = sessionAlreadyExisting;
        this.atLeastSixCharacters = atLeastSixCharacters;
        this.includeLowercaseCharacters = includeLowercaseCharacters;
        this.includeUppercaseCharacters = includeUppercaseCharacters;
        this.includeDigits = includeDigits;

    }
}
