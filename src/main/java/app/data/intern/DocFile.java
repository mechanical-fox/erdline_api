package app.data.intern;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class DocFile {
    
    String name;
    String html;
    LocalDateTime end;

    public DocFile(String name, String html, int expireIn){
        LocalDateTime now = LocalDateTime.now();
        this.end = now.plusSeconds(expireIn);
        this.name = name;
        this.html = html;
    }

    /** Return true if the expiration delay is expired for this time. */
    public boolean isExpired(){
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(end);
    }

}
