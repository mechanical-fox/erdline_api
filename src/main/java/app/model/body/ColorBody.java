package app.model.body;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColorBody {

    @Schema(example = "Orange")
    private String name;
    @Schema(example = "rgb(240, 138, 22)")
    private String firstGradient;
    @Schema(example = "rgb(231, 195, 36)")
    private String secondGradient;

    public ColorBody(){
        
        this.name = null;
        this.firstGradient = null;
        this.secondGradient = null;

    }

}