package africa.semicolon.com.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("User")
public class User {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private List<Post> posts = new ArrayList<>();
    private LocalDateTime dateCreated = LocalDateTime.now();
    private boolean isLoggedIn;
}
