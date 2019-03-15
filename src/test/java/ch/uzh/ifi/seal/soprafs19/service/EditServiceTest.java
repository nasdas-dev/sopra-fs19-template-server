package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class EditServiceTest {

    @Autowired
    private EditService editService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void canEditUser() {
        String username = "testUser";

        Assert.assertFalse(this.userService.existsUserByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        User createdUser = this.userService.createUser(testUser);

        var loggedUser = this.loginService.login(createdUser);

        Assert.assertTrue(this.editService.canEditUser(createdUser.getId(), loggedUser.getToken()));
        Assert.assertFalse(this.editService.canEditUser(Long.valueOf(0), loggedUser.getToken()));

        userRepository.delete(userRepository.findByUsername("testUser"));

    }
}