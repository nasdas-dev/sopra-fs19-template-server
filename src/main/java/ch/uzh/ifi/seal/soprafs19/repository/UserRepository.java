package ch.uzh.ifi.seal.soprafs19.repository;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
// Repositories = Access point to the database
// Define Spring Queries

@Primary
@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	User findByToken(String token);
	User findById(long id);

	Boolean existsByUsername(String username);
	Boolean existsUserByUsername(String username);

}
