package africa.semicolon.com.utils;

import africa.semicolon.com.data.models.User;
import africa.semicolon.com.dtos.request.UserRegisterRequest;

public class Mapper {
    public static User map(UserRegisterRequest userRegisterRequest){
        User user = new User();
        user.setFirstname(userRegisterRequest.getFirstname().toLowerCase());
        user.setLastname(userRegisterRequest.getLastname());
        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(userRegisterRequest.getPassword());
        return user;
    }
}
