package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;


import app.model.database.ExampleEntity;

public interface ExampleRepository  extends CrudRepository<ExampleEntity, Long> {
    
    @NativeQuery("SELECT * FROM example")
    public List<ExampleEntity> list();
}
