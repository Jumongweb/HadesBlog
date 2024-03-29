package africa.semicolon.com.data.repositories;

import africa.semicolon.com.data.models.View;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ViewRepository extends MongoRepository<View, String> {

}
