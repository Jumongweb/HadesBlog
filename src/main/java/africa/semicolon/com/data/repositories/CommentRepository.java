package africa.semicolon.com.data.repositories;

import africa.semicolon.com.data.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String>{
}
