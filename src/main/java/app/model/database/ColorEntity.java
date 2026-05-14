package app.model.database;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table( name="Background")
@Entity
public class ColorEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Schema(example = "1")
    Integer id;
    @Schema(example = "Orange")
    String name;
    @Schema(example = "rgb(240, 138, 22)")
    String firstGradient;
    @Schema(example = "rgb(231, 195, 36)")
    String secondGradient;

    public ColorEntity(){
        
        this.name = null;
        this.firstGradient = null;
        this.secondGradient = null;

    }

    public ColorEntity(String name, String firstGradient, String secondGradient){
        this.name = name;
        this.firstGradient = firstGradient;
        this.secondGradient = secondGradient;
    }

}
