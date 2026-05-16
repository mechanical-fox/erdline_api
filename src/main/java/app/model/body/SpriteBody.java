package app.model.body;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpriteBody {

    @Schema(example = "Adrien")
    String name;

    @Schema(example = "Adrien_480.png")
    String filename;

    @Schema(description = "", example = "data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHEl" +
    "EQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==")
    String data;

    public SpriteBody(){
        this.name = null;
        this.filename = null;
        this.data = null;
    }

    public SpriteBody(String name, String filename, String data){
        this.name = name;
        this.filename = filename;
        this.data = data;
    }
}
