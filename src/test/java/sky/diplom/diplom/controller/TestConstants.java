package sky.diplom.diplom.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.mock.web.MockMultipartFile;
import ru.skypro.homework.dto.AdsCommentDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsComment;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;

import java.io.IOException;
import java.time.Instant;

public class TestConstants {

    public static final MockMultipartFile IMAGE_FILE = new MockMultipartFile("image", new byte[0]);
    public static final Long ID = 1L;
    public static final String DESCRIPTION = "description";
    public static final int PRICE = 10;
    public static final String TITLE = "title";
    public static final String ADS_IMAGE_STRING = "/ads/image/2";
    public static final Image USER_IMAGE;

    static {
        try {
            USER_IMAGE = new Image(ID, 12, "png", IMAGE_FILE.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final Image ADS_IMAGE;

    static {
        try {
            ADS_IMAGE = new Image(2, 12, "png", IMAGE_FILE.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final User USER = new User(ID, "fn", "ln", "user",
            "password", "phone", "city", Instant.now(),
            USER_IMAGE, Role.USER);

    public static final JSONObject CREATE_ADS_DTO_JSON = new JSONObject();

    static {
        try {
            CREATE_ADS_DTO_JSON.put("description", DESCRIPTION);
            CREATE_ADS_DTO_JSON.put("price", PRICE);
            CREATE_ADS_DTO_JSON.put("title", TITLE);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static final AdsDto ADS_DTO = new AdsDto();

    static {
        ADS_DTO.setPk(ID);
        ADS_DTO.setAuthor(ID);
        ADS_DTO.setImage(ADS_IMAGE_STRING);
        ADS_DTO.setPrice(PRICE);
        ADS_DTO.setTitle(TITLE);
        ADS_DTO.setDescription(DESCRIPTION);
    }

    public static final Ads ADS = new Ads(ID, TITLE, DESCRIPTION, PRICE, USER, ADS_IMAGE);

    public static final JSONObject CREATE_ADS_DTO_COMMENT_JSON = new JSONObject();

    static {
        try {
            CREATE_ADS_DTO_COMMENT_JSON.put("author", 1);
            CREATE_ADS_DTO_COMMENT_JSON.put("created_at", Instant.now());
            CREATE_ADS_DTO_COMMENT_JSON.put("text", "Hello World");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static final AdsCommentDto ADS_DTO_COMMENT = new AdsCommentDto();

    static {
        ADS_DTO_COMMENT.setPk(1);
        ADS_DTO_COMMENT.setCreatedAt(String.valueOf(Instant.now()));
        ADS_DTO_COMMENT.setAuthor(1);
        ADS_DTO_COMMENT.setText("Hello World");
    }

    public static final AdsComment ADS_COMMENT = new AdsComment(ID,Instant.now(),"text",USER,ADS);
}
