package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class UserServiceTest {


    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Test
    public void createUser() {

        Assert.assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        User createdUser = userService.createUser(testUser);

        Assert.assertNotNull(createdUser.getId());
        Assert.assertEquals(createdUser.getName(), userRepository.findById(createdUser.getId().longValue()).getName());
        Assert.assertEquals(createdUser.getUsername(), userRepository.findById(createdUser.getId().longValue()).getUsername());
        Assert.assertNotNull(createdUser.getCreationDate());
        Assert.assertEquals(createdUser.getPassword(), userRepository.findById(createdUser.getId().longValue()).getPassword());
        Assert.assertNull(createdUser.getToken());
        Assert.assertEquals(createdUser.getStatus(),UserStatus.OFFLINE);
    }

    @Test
    public void existsUserByUsername() {
        String username = "testUsername1";

        Assert.assertFalse(userService.existsUserByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        userService.createUser(testUser);

        Assert.assertTrue(userService.existsUserByUsername(username));
    }

    @Test
    public void getUserById() {
        String username = "testUsername2";

        Assert.assertFalse(userService.existsUserByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        User createdUser = userService.createUser(testUser);

        Assert.assertEquals(createdUser.getId(), userService.getUserById(createdUser.getId().longValue()).getId());
    }

    @Test
    public void getUserByUsername() {
        String username = "testUsername3";

        Assert.assertFalse(userService.existsUserByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        User createdUser = userService.createUser(testUser);

        Assert.assertEquals(createdUser.getUsername(), userRepository.findById(createdUser.getId().longValue()).getUsername());
    }

    @Test
    public void updateUser() {
        String username = "testUsername4";

        Assert.assertFalse(userService.existsUserByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        User createdUser = userService.createUser(testUser);

        Assert.assertEquals(createdUser.getUsername(), userRepository.findById(createdUser.getId().longValue()).getUsername());

        User updatedRequestedUser = new User();
        updatedRequestedUser.setUsername(username+"Updated");
        updatedRequestedUser.setBirthday(new Date());

        userService.updateUser(createdUser.getId(), updatedRequestedUser);

        User updatedUser = this.userService.getUserById(createdUser.getId());

        Assert.assertEquals(updatedRequestedUser.getUsername(), updatedUser.getUsername());
    }

}
