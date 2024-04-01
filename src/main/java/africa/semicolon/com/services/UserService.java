package africa.semicolon.com.services;

import africa.semicolon.com.data.models.User;
import africa.semicolon.com.dtos.request.DeleteUserRequest;
import africa.semicolon.com.dtos.request.LoginRequest;
import africa.semicolon.com.dtos.request.UserRegisterRequest;
import africa.semicolon.com.dtos.response.RegisterRequestResponse;
import org.springframework.stereotype.Service;


public interface UserService {
    RegisterRequestResponse register(UserRegisterRequest userRegisterRequest);

    void login(LoginRequest loginRequest);

    long countUser();

    User findUserBy(String username);

    void deleteUserBy(DeleteUserRequest deleteUserRequest);
}
