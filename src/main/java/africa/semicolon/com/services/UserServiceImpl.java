package africa.semicolon.com.services;

import africa.semicolon.com.data.models.User;
import africa.semicolon.com.data.repositories.UserRepository;
import africa.semicolon.com.dtos.request.DeleteUserRequest;
import africa.semicolon.com.dtos.request.LoginRequest;
import africa.semicolon.com.dtos.request.UserRegisterRequest;
import africa.semicolon.com.dtos.response.RegisterRequestResponse;
import africa.semicolon.com.exceptions.InvalidPasswordException;
import africa.semicolon.com.exceptions.LoggedInException;
import africa.semicolon.com.exceptions.UserNotFoundException;
import africa.semicolon.com.exceptions.UsernameExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static africa.semicolon.com.utils.Mapper.map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


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
                user.setLocked(false);
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
    public User findUserBy(String username) {
        User foundUser = userRepository.findUserByUsername(username);
        if (foundUser == null) throw new UserNotFoundException("User does not exist");
        return foundUser;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    public void deleteUserBy(DeleteUserRequest deleteUserRequest) {
        User deletedUser = findUserBy(deleteUserRequest.getUsername());
        if (deletedUser == null) {
            throw new UserNotFoundException("User does not exist");
        }
        userRepository.delete(deletedUser);
    }

    @Override
    public boolean isLoggedIn(String username) {
        User user = userRepository.findUserByUsername(username);
        return user.isLocked();
    }

    public void isLocked(String username) {
        for (User user : userRepository.findAll()) {
            if (user.isLocked()) throw new LoggedInException("You need to login to perform this action");
        }
    }

    public boolean getLockedStatus(String username){
        User user = findUserBy(username);
        return user.isLocked();
    }

    public void validate (String username){
        boolean userExist = userRepository.existsByUsername(username);
        if (userExist) throw new UsernameExistException(String.format("%s already exist", username));
    }

}
