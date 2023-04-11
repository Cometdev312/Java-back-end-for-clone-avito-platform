package sky.diplom.diplom.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.controller.AdsController;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdsCommentRepository;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sky.diplom.diplom.controller.TestConstants.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class AdsControllerTest {

    @Autowired
    AdsController adsController;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    AdsRepository adsRepository;
    @Autowired
    AdsCommentRepository commentRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertThat(adsController).isNotNull();
    }

    @BeforeEach
    void setUp() {
        imageRepository.save(USER_IMAGE);
        userRepository.save(USER);
    }

    void addAdAndCommentToRepositoriesForSomeTests() {
        imageRepository.save(ADS_IMAGE);
        adsRepository.save(ADS);
        commentRepository.save(ADS_COMMENT);
    }

    @Test
    void getAllAds() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.results").isEmpty());
    }

    @Test
    @WithMockUser
    void addAds() throws Exception {
        mockMvc.perform(multipart("/ads")
                        .file(IMAGE_FILE)
                        .part(new MockPart("properties", CREATE_ADS_DTO_JSON.toString().getBytes()))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pk").value(ID))
                .andExpect(jsonPath("$.author").value(ID))
                .andExpect(jsonPath("$.image").value(ADS_IMAGE_STRING))
                .andExpect(jsonPath("$.price").value(PRICE))
                .andExpect(jsonPath("$.title").value(TITLE))
                .andDo(print());
    }

    @Test
    @WithMockUser
    void getAdsMe() throws Exception {
        mockMvc.perform(get("/ads/me"))
                .andDo(print())
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.results").isEmpty());
    }

    @Test
    @WithMockUser
    void getFullAd() throws Exception {
        addAdAndCommentToRepositoriesForSomeTests();

        mockMvc.perform(get("/ads/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.image").value(ADS_IMAGE_STRING))
                .andExpect(jsonPath("$.authorLastName").value(USER.getLastName()))
                .andExpect(jsonPath("$.authorFirstName").value(USER.getFirstName()))
                .andExpect(jsonPath("$.phone").value(USER.getPhone()))
                .andExpect(jsonPath("$.price").value(ADS.getPrice()))
                .andExpect(jsonPath("$.description").value(ADS.getDescription()))
                .andExpect(jsonPath("$.pk").value(ADS.getId()))
                .andExpect(jsonPath("$.title").value(ADS.getTitle()))
                .andExpect(jsonPath("$.email").value(USER.getEmail()));
    }

    @Test
    @WithMockUser
    void getFullAdThrowsNotFoundExceptionIfAdNotFound() throws Exception {
        mockMvc.perform(get("/ads/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getAdsComment() throws Exception {
        addAdAndCommentToRepositoriesForSomeTests();

        mockMvc.perform(get("/ads/1/comments/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pk").value(ADS_COMMENT.getId()))
                .andExpect(jsonPath("$.author").value(ADS_COMMENT.getAuthor().getId()))
                .andExpect(jsonPath("$.text").value("text"));
    }

    @Test
    @WithMockUser
    void deleteAdsComment() throws Exception {
        addAdAndCommentToRepositoriesForSomeTests();

        mockMvc.perform(delete("/ads/1/comments/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void updateComments() throws Exception {
        addAdAndCommentToRepositoriesForSomeTests();

        JSONObject createAdsDtoCommentJson = new JSONObject();
        createAdsDtoCommentJson.put("pk", 1);
        createAdsDtoCommentJson.put("author", 1);
        createAdsDtoCommentJson.put("text", "test");
        createAdsDtoCommentJson.put("createdAt", Instant.now());

        mockMvc.perform(patch("/ads/1/comments/1")
                        .content(createAdsDtoCommentJson.toString())
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.pk").value(ADS_COMMENT.getId()))
                .andExpect(jsonPath("$.author").value(ADS_COMMENT.getAuthor().getId()))
                .andExpect(jsonPath("$.text").value("test"));
    }

    @Test
    @WithMockUser
    void updateAds() throws Exception {
        addAdAndCommentToRepositoriesForSomeTests();

        JSONObject createAdsDtoJson = new JSONObject();
        createAdsDtoJson.put("description", "new description");
        createAdsDtoJson.put("price", 20_000);
        createAdsDtoJson.put("title", "new title");

        mockMvc.perform(patch("/ads/1")
                        .content(createAdsDtoJson.toString())
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pk").value(ID))
                .andExpect(jsonPath("$.author").value(ID))
                .andExpect(jsonPath("$.image").value(ADS_IMAGE_STRING))
                .andExpect(jsonPath("$.price").value(20_000))
                .andExpect(jsonPath("$.title").value("new title"));
    }

    @Test
    @WithMockUser
    void updateAdsImage() throws Exception {
        addAdAndCommentToRepositoriesForSomeTests();

        MockPart partFile = new MockPart("image", "image", new byte[3]);

        mockMvc.perform(patch("/ads/1/image")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(request -> {
                            request.addPart(partFile);
                            return request;
                        }))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void removeAds() throws Exception {
        addAdAndCommentToRepositoriesForSomeTests();

        assertThat(adsRepository.findById(ID).get()).isEqualTo(ADS);

        mockMvc.perform(delete("/ads/1"))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(adsRepository.findById(ID)).isEmpty();
    }

    @Test
    @WithMockUser(value = "email", password = "0123456789")
    void removeAdsThrowsAccessDeniedExceptionIfUserIsNotCreatorOfAdNorAdmin() throws Exception {
        addAdAndCommentToRepositoriesForSomeTests();

        User stranger = new User(2, "test", "test", "email", "0123456789",
                "+79998887766", "city", Instant.now(), null, Role.USER);
        userRepository.save(stranger);

        mockMvc.perform(delete("/ads/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void getComments() throws Exception {
        addAdAndCommentToRepositoriesForSomeTests();

        mockMvc.perform(get("/ads/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.results").isNotEmpty());
    }

    @Test
    @WithMockUser
    void addAdsComments() throws Exception {
        imageRepository.save(ADS_IMAGE);
        adsRepository.save(ADS);

        mockMvc.perform(post("/ads/1/comments")
                        .content(CREATE_ADS_DTO_COMMENT_JSON.toString())
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pk").value(1))
                .andExpect(jsonPath("$.author").value(1))
                .andExpect(jsonPath("$.text").value("Hello World"));
    }

    @Test
    void getAdsImage() throws Exception {
        addAdAndCommentToRepositoriesForSomeTests();

        mockMvc.perform(get("/ads/image/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().bytes(ADS_IMAGE.getData()));
    }
}
