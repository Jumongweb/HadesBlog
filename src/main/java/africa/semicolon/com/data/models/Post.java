package africa.semicolon.com.data.models;

import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("Posts")
public class Post {
    @Id
    private String id;
    private String title;
    private String content;
    private String user;
    @DBRef
    private List<Comment> comments;
    @DBRef
    private List<View> view;
    private LocalDateTime dateCreated;
}
