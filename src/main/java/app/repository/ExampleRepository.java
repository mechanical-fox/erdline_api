package app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;
import app.data.database.ExampleEntity;


public interface ExampleRepository  extends CrudRepository<ExampleEntity, Integer> {
    

    @NativeQuery("select * from EXAMPLE where html_id = ?1")
    List<ExampleEntity> queryByHtmlId(String uuid);

}
