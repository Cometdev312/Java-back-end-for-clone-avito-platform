package sky.diplom.diplom.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseWrapperAds {

    private int count;
    private List<AdsDto> result = new ArrayList<>();

}
