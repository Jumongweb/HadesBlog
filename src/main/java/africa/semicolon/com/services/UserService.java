package africa.semicolon.com.services;

import africa.semicolon.com.dtos.request.UserRegisterRequest;

public interface UserService {
    void register(UserRegisterRequest userRegisterRequest);
}
