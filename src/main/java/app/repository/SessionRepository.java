package app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;

import app.model.database.SessionEntity;


public interface SessionRepository  extends CrudRepository<SessionEntity, Long> {
    
    @NativeQuery("SELECT * FROM registered_session")
    public List<SessionEntity> list();

    @NativeQuery("SELECT * FROM registered_session WHERE session = ?1")
    public List<SessionEntity> searchBySession(String session);

}
