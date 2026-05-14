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
    Long id;

    @Schema(example = "Adrien")
    String name;

    @Column(name = "data", length = -1)
    @Schema(description = "", example = "data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHEl" +
    "EQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==")
    String data;

    public SpriteEntity(){
        this.name = null;
        this.data = null;
    }

    public SpriteEntity(String name, String data){
        this.name = name;
        this.data = data;
    }

}
