package app.data.database;

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
    Integer id;
    String htmlId;
    String name;
    @Column(name = "HTML", length = -1)
    String html;

    public ExampleEntity(){
        this.htmlId = null;
        this.name = null;
        this.html = null;
    }

    public ExampleEntity(String htmlId, String name, String html){
        this.htmlId = htmlId;
        this.name = name;
        this.html = html;
    }
}
