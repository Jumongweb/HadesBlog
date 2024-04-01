package africa.semicolon.com.dtos.response;

import lombok.Data;

@Data
public class RegisterRequestResponse {
    private String id;
    private String username;
    private String dataRegistered;
}
