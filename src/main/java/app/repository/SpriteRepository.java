package app.repository;

import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;


import app.model.database.SpriteEntity;

import java.util.List;

public interface SpriteRepository extends CrudRepository<SpriteEntity, Long>{
    

    @NativeQuery("SELECT * FROM sprite")
    public List<SpriteEntity> list();

}
