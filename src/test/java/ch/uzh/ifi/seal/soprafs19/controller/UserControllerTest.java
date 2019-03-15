package ch.uzh.ifi.seal.soprafs19.controller;
import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.controller.UserController;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
@AutoConfigureMockMvc
public class UserControllerTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void createUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Assert.assertNull(userRepository.findByUsername("testUser"));

        User testUser = new User();
        testUser.setId(3L);
        testUser.setName("testName");
        testUser.setUsername("testUser");
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        String json = mapper.writeValueAsString(testUser);

        this.mvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("users/" + testUser.getId()));


        userRepository.delete(userRepository.findByUsername("testUser"));

    }

    @Test
    public void userAlreadyExists() throws Exception {

        Assert.assertNull(userRepository.findByUsername("testUser"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUser");
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        userService.createUser(testUser);

        Assert.assertNotNull(userRepository.findByUsername("testUser"));

        this.mvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testName\",\"username\": \"testUser\", \"password\": \"testPassword\"}"))
                .andExpect(status().is(409));

        userRepository.delete(userRepository.findByUsername("testUser"));

    }

    @Test
    public void updateUser() throws Exception {

        User testUser = new User();
        testUser.setId(1L);
        testUser.setName("testName");
        testUser.setUsername("testUser");
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        userService.createUser(testUser);

        this.mvc.perform(put("/users/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\": \"testUserUpdated\", \"birthday\": \"1970-01-01\"}"))
                .andExpect(status().is(204));

        userRepository.delete(userRepository.findByUsername("testUserUpdated"));

    }

}
