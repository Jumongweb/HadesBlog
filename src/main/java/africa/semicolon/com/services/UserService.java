package africa.semicolon.com.services;

import africa.semicolon.com.dtos.request.UserRegisterRequest;
import org.springframework.stereotype.Service;


public interface UserService {
    void register(UserRegisterRequest userRegisterRequest);

    long countUser();
}
