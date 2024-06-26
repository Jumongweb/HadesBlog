package africa.semicolon.com.services;

import africa.semicolon.com.data.models.Post;
import africa.semicolon.com.data.models.User;
import africa.semicolon.com.dtos.request.DeleteUserRequest;
import africa.semicolon.com.dtos.request.LoginRequest;
import africa.semicolon.com.dtos.request.PostRequest;
import africa.semicolon.com.dtos.request.UserRegisterRequest;
import africa.semicolon.com.dtos.response.CreatePostResponse;
import africa.semicolon.com.dtos.response.LogoutRequest;
import africa.semicolon.com.dtos.response.RegisterRequestResponse;

import java.util.List;


public interface UserService {
    RegisterRequestResponse register(UserRegisterRequest userRegisterRequest);

    void login(LoginRequest loginRequest);

    long countUser();

    User findUserByUsername(String username);

    List<User> findAll();
    void deleteUserBy(DeleteUserRequest deleteUserRequest);

    boolean isLoggedIn(String username);


    void logout(LogoutRequest logoutRequest);

    CreatePostResponse createPost(PostRequest postRequest);

    Post findAllPostBelongingTo(String username);
}
