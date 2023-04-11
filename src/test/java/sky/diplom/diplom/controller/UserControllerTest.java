package sky.diplom.diplom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.controller.UserController;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;

import java.time.Instant;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserController userController;
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        Assertions.assertThat(userController).isNotNull();
    }

    @WithMockUser(value = "a@mail.ru", password = "12345678")
    @Test
    void updateUser() throws Exception {
        userRepository.save(new User(1L, "fgsfd", "fdsfsd", "a@mail.ru", "12345678",
                "446486568", null, Instant.now(), null, Role.USER));

        User user = new User();
        UserDto userDto = new UserDto();

        user.setEmail("a@mail.ru");
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setPhone("+79991254698");
        user.setRole(Role.USER);
        user.setPassword("123");

        userDto.setEmail("a@mail.ru");
        userDto.setFirstName("Ivan");
        userDto.setLastName("Ivanov");
        userDto.setPhone("+79991254698");

        mockMvc.perform(patch("/users/me").with(httpBasic("a@mail.ru","12345678"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.phone").value(user.getPhone()));
    }

    @WithMockUser(value = "a@mail.ru", password = "12345678")
    @Test
    void setPassword() throws Exception {
        userRepository.save(new User(1L, "fgsfd", "fdsfsd", "a@mail.ru", "12345678",
                "446486568", null, Instant.now(), null, Role.USER));


        NewPasswordDto passwordDto = new NewPasswordDto();
        passwordDto.setCurrentPassword("12345678");
        passwordDto.setNewPassword("87654321");

        when(passwordEncoder.matches("12345678", "12345678")).thenReturn(true);
        when(passwordEncoder.encode("87654321")).thenReturn("87654321");

        mockMvc.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPassword").value(passwordDto.getCurrentPassword()))
                .andExpect(jsonPath("$.newPassword").value(passwordDto.getNewPassword()));
    }

    @WithMockUser(value = "a@mail.ru", password = "12345678")
    @Test
    void updateUserImage() throws Exception {
        userRepository.save(new User(1L, "fgsfd", "fdsfsd", "a@mail.ru", "12345678",
                "446486568", null, Instant.now(), null, Role.USER));

        MockPart partFile = new MockPart("image", "image", new byte[3]);

        mockMvc.perform(patch("/users/me/image")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(request -> {
                            request.addPart(partFile);
                            return request;
                        }))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "a@mail.ru", password = "12345678")
    @Test
    void getUserById() throws Exception {
        User user = userRepository.save(new User(1L, "fgsfd", "fdsfsd", "a@mail.ru", "12345678",
                "446486568", null, Instant.now(), null, Role.USER));

        mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.phone").value(user.getPhone()));

    }

    @WithMockUser(value = "a@mail.ru", password = "12345678")
    @Test
    void getUser() throws Exception {
        User user = userRepository.save(new User(1L, "fgsfd", "fdsfsd", "a@mail.ru", "12345678",
                "446486568", null, Instant.now(), null, Role.USER));

        mockMvc.perform(get("/users/me"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.phone").value(user.getPhone()));

    }

    @WithMockUser(value = "a@mail.ru", password = "12345678")
    @Test
    void getImageById() throws Exception {
        Image image = imageRepository.save(new Image(1L, 4, "png", new byte[1]));

        mockMvc.perform(get("/users/image/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().bytes(image.getData()));
    }

    @WithMockUser(value = "a@mail.ru", password = "12345678", authorities = "ADMIN")
    @Test
    void updateRole() throws Exception {
        User user = userRepository.save(new User(1L, "fgsfd", "fdsfsd", "a@mail.ru", "12345678",
                "446486568", null, Instant.now(), null, Role.ADMIN));

        mockMvc.perform(MockMvcRequestBuilders.put("/users/1/updateRole")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Role.USER))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}



