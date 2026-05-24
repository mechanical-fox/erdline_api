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
@Table( name="REGISTERED_SESSION")
@Entity
public class SessionEntity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String session;
    private String hash_password; 
    private Boolean isAdmin;
    @Column(name = "json_backgrounds", length = -1)
    private String json_backgrounds;
    @Column(name = "json_characters", length = -1)
    private String json_characters;
    @Column(name = "json_scenes", length = -1)
    private String json_scenes;

    public SessionEntity(){
        this.id = null;
        this.session = null;
        this.hash_password = null;
        this.isAdmin = null;
        this.json_backgrounds = null;
        this.json_scenes = null;
    }

    public SessionEntity(String session, String hash_password, boolean isAdmin, String json_backgrounds, String json_characters,
        String json_scenes){
        this.session = session;
        this.hash_password = hash_password;
        this.isAdmin = isAdmin;
        this.json_backgrounds = json_backgrounds;
        this.json_characters = json_characters;
        this.json_scenes = json_scenes;
    }

}
