package africa.semicolon.com.dtos.request;

import africa.semicolon.com.exceptions.HadesBlogException;
import lombok.Data;

@Data
public class DeleteUserRequest {
     private String username;
     private String password;
}
