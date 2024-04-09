package africa.semicolon.com.utils;

import africa.semicolon.com.data.models.Post;
import africa.semicolon.com.data.models.User;
import africa.semicolon.com.dtos.request.PostRequest;
import africa.semicolon.com.dtos.request.UserRegisterRequest;
import africa.semicolon.com.dtos.response.CreatePostResponse;
import africa.semicolon.com.dtos.response.RegisterRequestResponse;

import java.time.format.DateTimeFormatter;

public class Mapper {
    public static User map(UserRegisterRequest userRegisterRequest){
        User user = new User();
        user.setFirstname(userRegisterRequest.getFirstname().toLowerCase());
        user.setLastname(userRegisterRequest.getLastname());
        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(userRegisterRequest.getPassword());
        return user;
    }

    public static RegisterRequestResponse map(User user){
        RegisterRequestResponse registerRequestResponse = new RegisterRequestResponse();
        registerRequestResponse.setId(user.getId());
        registerRequestResponse.setUsername(user.getUsername());
        registerRequestResponse.setDataRegistered(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(user.getDateCreated()));
        return registerRequestResponse;
    }

    public static Post mapCreatePost(PostRequest PostRequest){
        Post post = new Post();
        post.setTitle(PostRequest.getTitle());
        post.setContent(PostRequest.getContent());
        post.setUser(PostRequest.getUser());
        return post;
    }

    public static CreatePostResponse mapCreatePostResponse(Post post){
        CreatePostResponse response = new CreatePostResponse();
        response.setMessage("Post created successfully");
        return response;
    }

    public static UserRegisterRequest mapRegister1(UserRegisterRequest userRegisterRequest){
        userRegisterRequest.setFirstname("firstname1");
        userRegisterRequest.setLastname("lastname1");
        userRegisterRequest.setUsername("username1");
        userRegisterRequest.setPassword("password1");
        return userRegisterRequest;
    }

    public static UserRegisterRequest mapRegister2(UserRegisterRequest userRegisterRequest){
        userRegisterRequest.setFirstname("firstname2");
        userRegisterRequest.setLastname("lastname2");
        userRegisterRequest.setUsername("username2");
        userRegisterRequest.setPassword("password2");
        return userRegisterRequest;
    }
}
