package africa.semicolon.com.dtos.request;

import lombok.Data;

@Data
public class PostRequest {
    private String title;
    private String content;
    private String user;
}
