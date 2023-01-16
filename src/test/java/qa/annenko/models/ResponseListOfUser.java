package qa.annenko.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseListOfUser {

    private int page, per_page, total, total_pages;
    private List<ListOfUserData> data;
}
