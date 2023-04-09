package sky.diplom.diplom.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseWrapperComment {

    private int count;
    private List<AdsCommentDto> result = new ArrayList<>();

}
