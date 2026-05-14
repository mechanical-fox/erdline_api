package app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;

import app.model.database.ColorEntity;


public interface ColorRepository  extends CrudRepository<ColorEntity, Long> {
    
    @NativeQuery("SELECT * FROM color")
    public List<ColorEntity> list();

}
