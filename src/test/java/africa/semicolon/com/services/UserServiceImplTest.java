package africa.semicolon.com.services;

import africa.semicolon.com.data.models.User;
import africa.semicolon.com.data.repositories.UserRepository;
import africa.semicolon.com.dtos.request.DeleteUserRequest;
import africa.semicolon.com.dtos.request.LoginRequest;
import africa.semicolon.com.dtos.request.PostRequest;
import africa.semicolon.com.dtos.request.UserRegisterRequest;
import africa.semicolon.com.exceptions.InvalidPasswordException;
import africa.semicolon.com.exceptions.UserNotFoundException;
import africa.semicolon.com.exceptions.UsernameExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void initializer(){
        userRepository.deleteAll();
    }
    @Test
    public void testThatUserServiceCanCreateUser(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        assertEquals(1, userService.countUser());
    }

    @Test
    public void UserServiceThrowExceptionIfUserWithSameUsernameIsSaved(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        assertThrows(UsernameExistException.class, ()->userService.register(userRegisterRequest));
        assertEquals(1, userService.countUser());
    }
    @Test
    public void testUserServiceCreate3UserServiceCountIsThree(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);

        UserRegisterRequest userRegisterRequest2 = new UserRegisterRequest();
        userRegisterRequest2.setFirstname("firstname");
        userRegisterRequest2.setLastname("lastname");
        userRegisterRequest2.setUsername("username2");
        userRegisterRequest2.setPassword("password");
        userService.register(userRegisterRequest2);

        UserRegisterRequest userRegisterRequest3 = new UserRegisterRequest();
        userRegisterRequest3.setFirstname("firstname");
        userRegisterRequest3.setLastname("lastname");
        userRegisterRequest3.setUsername("username3");
        userRegisterRequest3.setPassword("password");
        userService.register(userRegisterRequest3);
        assertEquals(3, userService.countUser());
    }

    //@Test
    //public void testThatBlogAppIsLockedByDefault_LoginInAndItUnlock(){

    //}


    @Test
    public void testThatUserServiceCanFindUser(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        assertEquals(1, userService.countUser());
        User foundUser = userService.findUserBy("username");
        assertEquals(foundUser.getUsername(), "username");
    }

    @Test
    public void testFindUserThatDoesNotExistUserServiceThrowException(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        assertEquals(1, userService.countUser());
        User foundUser = userService.findUserBy("username");
        assertThrows(UserNotFoundException.class, ()->userService.findUserBy("wrongUsername"));
    }

    @Test
    public void testThatUserServiceCanDeleteUser(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        assertEquals(1, userService.countUser());
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUsername("username");
        deleteUserRequest.setPassword("password");
        userService.deleteUserBy(deleteUserRequest);
        assertEquals(0, userService.countUser());
    }

    @Test
    public void testDeleteUserThatDoesNotExitDiaryServiceThrowsException(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUsername("username2");
        deleteUserRequest.setPassword("password");
        assertEquals(1, userService.countUser());
        assertThrows(UserNotFoundException.class, ()->userService.deleteUserBy(deleteUserRequest));
        assertEquals(1, userService.countUser());
    }

    @Test
    public void testUserServiceCanFindAllUser(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username1");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);

        UserRegisterRequest userRegisterRequest2 = new UserRegisterRequest();
        userRegisterRequest2.setFirstname("firstname");
        userRegisterRequest2.setLastname("lastname");
        userRegisterRequest2.setUsername("username2");
        userRegisterRequest2.setPassword("password");
        userService.register(userRegisterRequest2);

        UserRegisterRequest userRegisterRequest3 = new UserRegisterRequest();
        userRegisterRequest3.setFirstname("firstname");
        userRegisterRequest3.setLastname("lastname");
        userRegisterRequest3.setUsername("username3");
        userRegisterRequest3.setPassword("password");
        userService.register(userRegisterRequest3);
        assertEquals(3, userService.countUser());
        User nothing = new User();
        User foundUser1 = userService.findUserBy("username1");
        User foundUser2 = userService.findUserBy("username2");
        User foundUser3 = userService.findUserBy("username3");
        List<User> allUsers = new ArrayList<>();
        allUsers.add(foundUser1);
        allUsers.add(foundUser2);
        allUsers.add(foundUser3);
        assertEquals(userService.findAll(), allUsers);
    }

    @Test
    public void testThatUserCanLogIn(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        assertTrue(userService.isLoggedIn("username"));
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        assertFalse(userService.isLoggedIn("username"));
    }

    @Test
    public void testThatUserCanCannotLoggedInWithWrongUsername(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("wrongUsername");
        loginRequest.setPassword("password");
        assertThrows(UserNotFoundException.class, ()->userService.login(loginRequest));
    }

    @Test
    public void testThatUserCanCannotLoggedInWithWrongPassword(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("wrongPassword");
        assertThrows(InvalidPasswordException.class, ()->userService.login(loginRequest));
    }

    @Test
    public void testThatUserCanCreatePostWhileLoggedIn(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        assertTrue(userService.isLoggedIn("username"));
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        assertFalse(userService.isLoggedIn("username"));
        PostRequest postRequest = new PostRequest();

    }


}