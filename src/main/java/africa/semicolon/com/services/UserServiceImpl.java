package africa.semicolon.com.services;

import africa.semicolon.com.data.models.User;
import africa.semicolon.com.data.repositories.UserRepository;
import africa.semicolon.com.dtos.request.UserRegisterRequest;
import africa.semicolon.com.exceptions.UsernameExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void validate(String username){
        boolean userExist = userRepository.existsByUsername(username);
        if (userExist) throw new UsernameExistException(String.format("%s already exist", username));
    }


}
