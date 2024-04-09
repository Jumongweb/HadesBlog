package africa.semicolon.com.services;

import africa.semicolon.com.data.models.Post;
import africa.semicolon.com.data.models.User;
import africa.semicolon.com.data.repositories.PostRepository;
import africa.semicolon.com.data.repositories.UserRepository;
import africa.semicolon.com.dtos.request.DeleteUserRequest;
import africa.semicolon.com.dtos.request.LoginRequest;
import africa.semicolon.com.dtos.request.PostRequest;
import africa.semicolon.com.dtos.request.UserRegisterRequest;
import africa.semicolon.com.dtos.response.CreatePostResponse;
import africa.semicolon.com.dtos.response.LogoutRequest;
import africa.semicolon.com.dtos.response.RegisterRequestResponse;
import africa.semicolon.com.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static africa.semicolon.com.utils.Mapper.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public RegisterRequestResponse register(UserRegisterRequest userRegisterRequest) {
        validate(userRegisterRequest.getUsername());
        User savedUser = map(userRegisterRequest);
        RegisterRequestResponse response = map(savedUser);
        userRepository.save(savedUser);
        return response;
    }

    @Override
    public void login(LoginRequest loginRequest) {
        isValidUser(loginRequest.getUsername(), loginRequest.getPassword());
        for (User user : userRepository.findAll()) {
            if (user.getUsername().equals(loginRequest.getUsername())) {
                user.setLoggedIn(true);
                userRepository.save(user);
            }
        }
    }
    public void isValidUser(String username, String password){
        User user = userRepository.findUserByUsername(username);
        if (user == null) throw new UserNotFoundException("User does not exist");
        if (!(user.getPassword().equals(password))) throw new InvalidPasswordException("Invalid password");
    }

    @Override
    public long countUser() {
        return userRepository.count();
    }

    @Override
    public User findUserByUsername(String username) {
        var foundUser = userRepository.findUserByUsername(username);
        if (foundUser == null) throw new UserNotFoundException("User does not exist");
        return foundUser;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    public void deleteUserBy(DeleteUserRequest deleteUserRequest) {
        User deletedUser = findUserByUsername(deleteUserRequest.getUsername());
        if (deletedUser == null) {
            throw new UserNotFoundException("User does not exist");
        }
        userRepository.delete(deletedUser);
    }

    @Override
    public boolean isLoggedIn(String username) {
        User user = userRepository.findUserByUsername(username);
        return user.isLoggedIn();
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        var user = findUserByUsername(logoutRequest.getUsername());
        if (user == null) throw new UserNotFoundException("user not found");
        user.setLoggedIn(false);
        userRepository.save(user);
    }

    @Override
    public CreatePostResponse createPost(PostRequest postRequest) {
        String username = postRequest.getUser();
        String title = postRequest.getTitle();

        User user = findUserByUsername(username);
        validateLogin(username);
        validatePost(title);
        Post post = mapCreatePost(postRequest);
        postRepository.save(post);
        List<Post> posts = user.getPosts();
        posts.add(post);
        user.setPosts(posts);
        userRepository.save(user);
        return mapCreatePostResponse(post);
    }

    @Override
    public Post findAllPostBelongingTo(String username) {
        List<Post> posts = new ArrayList<>();
        postRepository.findAll();
        return null;
    }

    public void validatePost(String title){
        for (Post post : postRepository.findAll()){
            if (post.getTitle().equals(title)){
                throw new PostAlreadyExistException("Post already exist with this same title");
            }
        }
        if (title == null || title.isEmpty())
            throw new IllegalArgumentException("Title cannot be empty");
        if (title.length() > 20)
            throw new IllegalArgumentException("Title cannot be longer than 100 characters");
        if (title.length() < 3)
            throw new IllegalArgumentException("Title cannot be shorter than 3 characters");
    }

    public void validateLogin(String username){
        User user = findUserByUsername(username);
        if (!(user.isLoggedIn())) throw new LoggedInException("You need to login to perform this action");
    }

    public boolean getLockedStatus(String username){
        User user = findUserByUsername(username);
        return user.isLoggedIn();
    }

    public void validate (String username){
        boolean userExist = userRepository.existsByUsername(username);
        if (userExist) throw new UsernameExistException(String.format("%s already exist", username));
    }

}
