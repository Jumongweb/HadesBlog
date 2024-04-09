package africa.semicolon.com.utils;

import africa.semicolon.com.data.models.User;
import africa.semicolon.com.dtos.request.UserRegisterRequest;
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
}
