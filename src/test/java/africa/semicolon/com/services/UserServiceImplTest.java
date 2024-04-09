package africa.semicolon.com.services;

import africa.semicolon.com.data.models.User;
import africa.semicolon.com.data.repositories.PostRepository;
import africa.semicolon.com.data.repositories.UserRepository;
import africa.semicolon.com.dtos.request.*;
import africa.semicolon.com.dtos.response.LogoutRequest;
import africa.semicolon.com.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static africa.semicolon.com.utils.Mapper.mapRegister1;
import static net.bytebuddy.matcher.ElementMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

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


    @Test
    public void testThatUserServiceCanFindUser(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        assertEquals(1, userService.countUser());
        User foundUser = userService.findUserByUsername("username");
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
        User foundUser = userService.findUserByUsername("username");
        assertThrows(UserNotFoundException.class, ()->userService.findUserByUsername("wrongUsername"));
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
        User foundUser1 = userService.findUserByUsername("username1");
        User foundUser2 = userService.findUserByUsername("username2");
        User foundUser3 = userService.findUserByUsername("username3");
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
        assertEquals(1, userService.countUser());
        assertFalse(userService.isLoggedIn("username"));
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        assertEquals(1, userService.countUser());
        assertTrue(userService.isLoggedIn("username"));
    }

    @Test
    public void testThatUserServiceCanLoginTwoUsers(){
        UserRegisterRequest userRegisterRequest1 = new UserRegisterRequest();
        UserRegisterRequest userRegisterRequest2 = new UserRegisterRequest();
        userRegisterRequest1.setFirstname("firstname1");
        userRegisterRequest1.setLastname("lastname1");
        userRegisterRequest1.setUsername("username1");
        userRegisterRequest1.setPassword("password1");
        userService.register(userRegisterRequest1);
        userRegisterRequest2.setFirstname("firstname2");
        userRegisterRequest2.setLastname("lastname2");
        userRegisterRequest2.setUsername("username2");
        userRegisterRequest2.setPassword("password2");
        userService.register(userRegisterRequest2);
        assertEquals(2, userService.countUser());
        assertFalse(userService.isLoggedIn("username1"));
        assertFalse(userService.isLoggedIn("username2"));
        LoginRequest loginRequest1 = new LoginRequest();
        LoginRequest loginRequest2 = new LoginRequest();
        loginRequest1.setUsername("username1");
        loginRequest2.setUsername("username2");
        loginRequest1.setPassword("password1");
        loginRequest2.setPassword("password2");
        userService.login(loginRequest1);
        userService.login(loginRequest2);
        assertEquals(2, userService.countUser());
        assertTrue(userService.isLoggedIn("username1"));
        assertTrue(userService.isLoggedIn("username2"));
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
        UserRegisterRequest userRegisterRequest1 = new UserRegisterRequest();
        UserRegisterRequest userRegisterRequest2 = new UserRegisterRequest();
        userRegisterRequest1.setFirstname("firstname1");
        userRegisterRequest1.setLastname("lastname1");
        userRegisterRequest1.setUsername("username1");
        userRegisterRequest1.setPassword("password1");
        userRegisterRequest2.setFirstname("firstname2");
        userRegisterRequest2.setLastname("lastname2");
        userRegisterRequest2.setUsername("username2");
        userRegisterRequest2.setPassword("password2");
        userService.register(userRegisterRequest1);
        userService.register(userRegisterRequest2);
        assertFalse(userService.isLoggedIn("username1"));
        assertFalse(userService.isLoggedIn("username2"));
        LoginRequest loginRequest1 = new LoginRequest();
        LoginRequest loginRequest2 = new LoginRequest();
        loginRequest1.setUsername("username1");
        loginRequest2.setUsername("username2");
        loginRequest1.setPassword("password1");
        loginRequest2.setPassword("password2");
        userService.login(loginRequest1);
        userService.login(loginRequest2);
        assertTrue(userService.isLoggedIn("username1"));
        assertTrue(userService.isLoggedIn("username2"));
        PostRequest postRequest = new PostRequest();
    }

    @Test
    public void testThatUserCanLogOut(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        assertEquals(1, userService.countUser());
        assertFalse(userService.isLoggedIn("username"));
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        assertTrue(userService.isLoggedIn("username"));
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername(loginRequest.getUsername());
        userService.logout(logoutRequest);
        assertFalse(userService.isLoggedIn("username"));
    }

    @Test
    public void testThatUserCanLogInLogoutAndLoginAgain(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        assertEquals(1, userService.countUser());
        assertFalse(userService.isLoggedIn("username"));
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername(loginRequest.getUsername());
        userService.login(loginRequest);
        assertTrue(userService.isLoggedIn("username"));
        userService.logout(logoutRequest);
        assertFalse(userService.isLoggedIn("username"));
        userService.login(loginRequest);
        assertTrue(userService.isLoggedIn("username"));
    }

    @Test
    public void testThatUserServiceCanCreateBlog(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        assertEquals(1, userService.countUser());
        assertFalse(userService.isLoggedIn("username"));
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        assertEquals(1, userService.countUser());
        assertTrue(userService.isLoggedIn("username"));
        PostRequest postRequest = new PostRequest();
        postRequest.setUser("username");
        postRequest.setTitle("title");
        postRequest.setContent("content");
        userService.createPost(postRequest);
        assertEquals(1, userService.findUserByUsername(postRequest.getUser()).getPosts().size());
    }

    @Test
    public void testThatUserCannotCreateBlogWithSameTitle(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        assertEquals(1, userService.countUser());
        assertFalse(userService.isLoggedIn("username"));
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        assertEquals(1, userService.countUser());
        assertTrue(userService.isLoggedIn("username"));
        PostRequest postRequest = new PostRequest();
        postRequest.setUser("username");
        postRequest.setTitle("title");
        postRequest.setContent("content");
        userService.createPost(postRequest);
        PostRequest postRequest2 = new PostRequest();
        postRequest2.setUser("username");
        postRequest2.setTitle("title");
        postRequest2.setContent("content");
        assertThrows(PostAlreadyExistException.class, ()->userService.createPost(postRequest2));
        assertEquals(1, userService.findUserByUsername(postRequest.getUser()).getPosts().size());
    }

    @Test
    public void testThatUserCannotCreatePostIfNotLoggedIn(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        assertEquals(1, userService.countUser());
        assertFalse(userService.isLoggedIn("username"));
        PostRequest postRequest = new PostRequest();
        postRequest.setUser("username");
        postRequest.setTitle("title");
        postRequest.setContent("content");
        assertThrows(LoggedInException.class, ()->userService.createPost(postRequest));
    }

    @Test
    public void testThatUserCannotCreatePostIfUserDoesNotExist(){
        PostRequest postRequest = new PostRequest();
        postRequest.setUser("username");
        postRequest.setTitle("title");
        postRequest.setContent("content");
        assertThrows(UserNotFoundException.class, ()->userService.createPost(postRequest));
    }

    @Test
    public void testCreate3BlogUserBlogIs3(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        assertEquals(1, userService.countUser());
        assertFalse(userService.isLoggedIn("username"));
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        assertEquals(1, userService.countUser());
        assertTrue(userService.isLoggedIn("username"));
        PostRequest postRequest1 = new PostRequest();
        postRequest1.setUser("username");
        postRequest1.setTitle("title1");
        postRequest1.setContent("content1");
        userService.createPost(postRequest1);

        PostRequest postRequest2 = new PostRequest();
        postRequest2.setUser("username");
        postRequest2.setTitle("title2");
        postRequest2.setContent("content2");
        userService.createPost(postRequest2);

        PostRequest postRequest3 = new PostRequest();
        postRequest3.setUser("username");
        postRequest3.setTitle("title3");
        postRequest3.setContent("content3");
        userService.createPost(postRequest3);
        assertEquals(3, userService.findUserByUsername("username").getPosts().size());
    }

    @Test
    public void testThat3DifferentUserCanCreateBlog(){
        UserRegisterRequest userRegisterRequest1 = new UserRegisterRequest();
        userRegisterRequest1.setFirstname("firstname");
        userRegisterRequest1.setLastname("lastname");
        userRegisterRequest1.setUsername("username1");
        userRegisterRequest1.setPassword("password");

        UserRegisterRequest userRegisterRequest2 = new UserRegisterRequest();
        userRegisterRequest2.setFirstname("firstname");
        userRegisterRequest2.setLastname("lastname");
        userRegisterRequest2.setUsername("username2");
        userRegisterRequest2.setPassword("password");

        UserRegisterRequest userRegisterRequest3 = new UserRegisterRequest();
        userRegisterRequest3.setFirstname("firstname");
        userRegisterRequest3.setLastname("lastname");
        userRegisterRequest3.setUsername("username3");
        userRegisterRequest3.setPassword("password");

        userService.register(userRegisterRequest1);
        userService.register(userRegisterRequest2);
        userService.register(userRegisterRequest3);
        assertEquals(3, userService.countUser());

        assertFalse(userService.isLoggedIn("username1"));
        assertFalse(userService.isLoggedIn("username2"));
        assertFalse(userService.isLoggedIn("username3"));

        LoginRequest loginRequest1 = new LoginRequest();
        loginRequest1.setUsername("username1");
        loginRequest1.setPassword("password");
        LoginRequest loginRequest2 = new LoginRequest();
        loginRequest2.setUsername("username2");
        loginRequest2.setPassword("password");
        LoginRequest loginRequest3 = new LoginRequest();
        loginRequest3.setUsername("username3");
        loginRequest3.setPassword("password");

        userService.login(loginRequest1);
        userService.login(loginRequest2);
        userService.login(loginRequest3);

        assertTrue(userService.isLoggedIn("username1"));
        assertTrue(userService.isLoggedIn("username2"));
        assertTrue(userService.isLoggedIn("username3"));
        PostRequest postRequest1 = new PostRequest();
        postRequest1.setUser("username1");
        postRequest1.setTitle("title1");
        postRequest1.setContent("content1");
        userService.createPost(postRequest1);

        PostRequest postRequest2 = new PostRequest();
        postRequest2.setUser("username2");
        postRequest2.setTitle("title2");
        postRequest2.setContent("content2");
        userService.createPost(postRequest2);

        PostRequest postRequest3 = new PostRequest();
        postRequest3.setUser("username3");
        postRequest3.setTitle("title3");
        postRequest3.setContent("content3");
        userService.createPost(postRequest3);
        assertEquals(1, userService.findUserByUsername("username1").getPosts().size());
        assertEquals(1, userService.findUserByUsername("username2").getPosts().size());
        assertEquals(1, userService.findUserByUsername("username3").getPosts().size());
    }



    @Test
    public void testThatUserServiceCanFindBlog(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstname("firstname");
        userRegisterRequest.setLastname("lastname");
        userRegisterRequest.setUsername("username");
        userRegisterRequest.setPassword("password");
        userService.register(userRegisterRequest);
        assertEquals(1, userService.countUser());
        assertFalse(userService.isLoggedIn("username"));
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        assertEquals(1, userService.countUser());
        assertTrue(userService.isLoggedIn("username"));
        PostRequest postRequest = new PostRequest();
        postRequest.setUser("username");
        postRequest.setTitle("title");
        postRequest.setContent("content");
        userService.createPost(postRequest);
        assertEquals("title", userService.findUserByUsername("username").getPosts().get(0).getTitle());
    }

}