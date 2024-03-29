package africa.semicolon.com.services;

import africa.semicolon.com.data.models.User;
import africa.semicolon.com.data.repositories.UserRepository;
import africa.semicolon.com.dtos.request.UserRegisterRequest;
import africa.semicolon.com.exceptions.UserNotFoundException;
import africa.semicolon.com.exceptions.UsernameExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static africa.semicolon.com.utils.Mapper.map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void register(UserRegisterRequest userRegisterRequest) {
        validate(userRegisterRequest.getUsername());
        User savedUser = map(userRegisterRequest);
        userRepository.save(savedUser);
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
    public void deleteUserBy(String username) {
        User deletedUser = findUserBy(username);

    }


    public void validate(String username){
        boolean userExist = userRepository.existsByUsername(username);
        if (userExist) throw new UsernameExistException(String.format("%s already exist", username));
    }


}
