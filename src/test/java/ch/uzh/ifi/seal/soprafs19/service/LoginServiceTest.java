package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
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
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void canLogin() {
        String username = "testUser";
        String password = "testPassword";

        Assert.assertFalse(this.userService.existsUserByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword(password);

        this.userService.createUser(testUser);

        Assert.assertTrue(this.loginService.canLogin(username, password));
        Assert.assertFalse(this.loginService.canLogin(username, "wrong " + password));

        userRepository.delete(userRepository.findByUsername("testUser"));
    }

    @Test
    public void login() {
        String username = "testUser";
        String password = "testPassword";

        Assert.assertFalse(this.userService.existsUserByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword(password);

        User createdUser = this.userService.createUser(testUser);

        Assert.assertNull(createdUser.getToken());
        if (createdUser.getStatus() != UserStatus.OFFLINE) {
            Assert.fail();
        }

        createdUser = this.loginService.login(createdUser);

        Assert.assertNotNull(createdUser.getToken());
        if (createdUser.getStatus() != UserStatus.ONLINE) {
            Assert.fail();
        }
    }
}