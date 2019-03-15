package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.LoginService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EditUserControllerTest {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void canEditUserPass() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String username = "testUser";

        Assert.assertNull(userRepository.findByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        testUser = userService.createUser(testUser);
        testUser = loginService.login(testUser);

        String json = mapper.writeValueAsString(testUser.getId());

        this.mvc.perform(
                post("/users/1/edit")
                        .header("token", testUser.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        userRepository.delete(userRepository.findByUsername("testUser"));
    }
}