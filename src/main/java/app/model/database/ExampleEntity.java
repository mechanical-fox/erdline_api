package app.model.database;


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
@Table( name="EXAMPLE")
@Entity
public class ExampleEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "json_backgrounds", length = -1)
    private String json_backgrounds;
    @Column(name = "json_characters", length = -1)
    private String json_characters;
    @Column(name = "json_scenes", length = -1)
    private String json_scenes;
    
    public ExampleEntity(){
        this.json_backgrounds = null;
        this.json_characters = null;
        this.json_scenes = null;
    }

    public ExampleEntity(String json_backgrounds, String json_characters, String json_scenes){
        this.json_backgrounds = json_backgrounds;
        this.json_characters = json_characters;
        this.json_scenes = json_scenes;
    }
}
