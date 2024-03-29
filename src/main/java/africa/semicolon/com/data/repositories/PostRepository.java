package africa.semicolon.com.data.repositories;

import africa.semicolon.com.data.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {

}
