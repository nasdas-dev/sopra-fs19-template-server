package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User newUser) {

        if(userRepository.existsByUsername(newUser.getUsername())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "add user fails because username already exists");
        }
        else {
            newUser.setCreationDate(new Date());
            newUser.setStatus(UserStatus.OFFLINE);
            userRepository.save(newUser);
            log.debug("Created Information for User: {}", newUser);
            return newUser;
        }
    }

    public boolean existsUserByUsername(String username) {
        return this.userRepository.existsUserByUsername(username);
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public void updateUser(long id, User updatedUser) {
        User oldUser = this.userRepository.findById(id);
        oldUser.setUsername(updatedUser.getUsername());
        oldUser.setBirthday(updatedUser.getBirthday());
        this.userRepository.save(oldUser);
    }
}
