package africa.semicolon.com.services;

import africa.semicolon.com.data.models.User;
import africa.semicolon.com.data.repositories.UserRepository;
import africa.semicolon.com.dtos.request.UserRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void register(UserRegisterRequest userRegisterRequest) {
        User user = new User();
        user.setFirstname(userRegisterRequest.getFirstname());
        user.setLastname(userRegisterRequest.getLastname());
        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(userRegisterRequest.getPassword());
        userRepository.save(user);
    }
}
