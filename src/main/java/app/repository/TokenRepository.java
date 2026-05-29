package app.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;

import app.model.database.TokenEntity;
import app.model.database.SessionEntity;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TokenRepository extends CrudRepository<TokenEntity, Long> {
    
    @Modifying
    @Transactional
    @NativeQuery("DELETE FROM registered_token WHERE expiration_date < NOW()")
    public void deleteExpiredTokens();

    @NativeQuery("SELECT rs.* FROM registered_token rt INNER JOIN registered_session rs ON rt.fk_session = rs.id" + 
    " WHERE rt.token = ?1 and rt.expiration_date > NOW();")
    public List<SessionEntity> queryBearerByToken(String token);
}