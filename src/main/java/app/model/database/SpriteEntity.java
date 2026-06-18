package app.model.database;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table( name="SPRITE")
@Entity
public class SpriteEntity {
    

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;

    @Schema(example = "Adrien")
    private String name;

    @Schema(example = "Adrien_480.png")
    private String filename;

    @Column(name = "data", length = -1)
    @Schema(description = "", example = "data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHEl" +
    "EQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==")
    private String data;

    public SpriteEntity(){
        this.name = null;
        this.filename = null;
        this.data = null;
    }

    public SpriteEntity(String name, String filename, String data){
        this.name = name;
        this.filename = filename;
        this.data = data;
    }

}
