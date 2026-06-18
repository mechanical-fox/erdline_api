package app.util;

import java.util.List;

import org.springframework.http.HttpHeaders;

import app.model.database.SessionEntity;
import app.repository.TokenRepository;

public class AuthUtil {
    
    /** Given the headers send by the user, returns the user / the session matching. If no token is present in the header
    * Authorization, or if the token is incorrect, null is returned. */
    public static SessionEntity identifySession(HttpHeaders headers, TokenRepository tokenRepository){
        List<String> authorizations = headers.get("Authorization");

        if(authorizations == null || authorizations.size() == 0)
            return null;

        String[] parts = authorizations.get(0).trim().split(" ");

        if(parts.length != 2 || !"Bearer".equals(parts[0]))
            return null;

        List<SessionEntity> sessions = tokenRepository.queryBearerByToken(parts[1]);

        if(sessions.size() == 0)
            return null;
        else
            return sessions.get(0);
    }
}
