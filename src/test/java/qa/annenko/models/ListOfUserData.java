package qa.annenko.models;

import lombok.Data;

@Data
public class ListOfUserData {

    private int id;
    private String email, first_name, last_name, avatar;
}
