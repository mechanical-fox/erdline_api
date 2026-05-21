package app.model.database;


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

    public SessionEntity(){
        this.id = null;
        this.session = null;
        this.hash_password = null;
        this.isAdmin = null;
    }

    public SessionEntity(String session, String hash_password, boolean isAdmin){
        this.session = session;
        this.hash_password = hash_password;
        this.isAdmin = isAdmin;
    }

}
