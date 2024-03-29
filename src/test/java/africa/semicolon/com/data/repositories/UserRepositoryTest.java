package africa.semicolon.com.data.repositories;

import africa.semicolon.com.data.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testThatRepositoryCanSaveUser(){
        userRepository.deleteAll();
        User user = new User();
        userRepository.save(user);
        assertThat(userRepository.count(), is(1L));
    }

    @Test
    public void testSaveThreeUserRepositoryCountIs3(){
        userRepository.deleteAll();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        assertThat(userRepository.count(), is(3L));
    }

}