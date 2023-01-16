package qa.annenko.tests;

import org.junit.jupiter.api.Test;
import qa.annenko.models.*;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static qa.annenko.specs.CommonRequestSpec.commonRequestSpec;
import static qa.annenko.specs.CreateUserRequestSpec.createUserRequestSpec;
import static qa.annenko.specs.CreateUserResponseSpec.createUserResponseSpec;
import static qa.annenko.specs.DeleteUserResponseSpec.deleteUserResponseSpec;
import static qa.annenko.specs.ListOfUserResponseSpec.listOfUserResponseSpec;
import static qa.annenko.specs.SingleUserNotFoundResponseSpec.singleUserNotFoundResponseSpec;
import static qa.annenko.specs.UnsuccessfulLoginRequestSpec.unsuccessfulLoginRequestSpec;
import static qa.annenko.specs.UnsuccessfulLoginResponseSpec.unsuccessfulLoginResponseSpec;
import static qa.annenko.specs.UpdateUserResponseSpec.updateUserResponseSpec;

public class ActionsWithUserTests {

    @Test
    public void totalOfGetListOfUserTest() {
        ResponseListOfUser response = given()
                .spec(commonRequestSpec)
                .when()
                .get("?page=2")
                .then()
                .spec(listOfUserResponseSpec)
                .extract().as(ResponseListOfUser.class);
        assertEquals(response.getTotal(), 12);
    }

    @Test
    public void itemsOfGetListOfUserTest() {
        ResponseListOfUser response = given()
                .spec(commonRequestSpec)
                .when()
                .get("?page=2")
                .then()
                .spec(listOfUserResponseSpec)
                .extract().as(ResponseListOfUser.class);
        assertThat(response.getData().get(0).getLast_name().equals("Lawson"));
        assertThat(response.getData().get(1).getLast_name().equals("Ferguson"));
        assertThat(response.getData().get(2).getLast_name().equals("Funke"));
    }

    @Test
    public void singleUserNotFoundTest() {

        given()
                .spec(commonRequestSpec)
                .when()
                .get("/23")
                .then()
                .spec(singleUserNotFoundResponseSpec);
    }

    @Test
    public void createUserTest() {

        RequestCreateUser bodyRequest = new RequestCreateUser();
        bodyRequest.setName("morpheus");
        bodyRequest.setJob("leader");

        ResponseCreateUser response = given()
                .spec(createUserRequestSpec)
                .body(bodyRequest)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .spec(createUserResponseSpec)
                .extract().as(ResponseCreateUser.class);
        assertThat(response.getName().equals("morpheus"));
        assertThat(response.getJob().equals("leader"));
    }

    @Test
    public void updateUserTest() {

        RequestCreateUser bodyRequest = new RequestCreateUser();
        bodyRequest.setName("morpheus");
        bodyRequest.setJob("zion resident");

        ResponseCreateUser response = given()
                .spec(createUserRequestSpec)
                .body(bodyRequest)
                .when()
                .put("/2")
                .then()
                .spec(updateUserResponseSpec)
                .extract().as(ResponseCreateUser.class);
        assertThat(response.getName().equals("morpheus"));
        assertThat(response.getJob().equals("zion resident"));
    }

    @Test
    public void deleteUserTest() {

        given()
                .spec(commonRequestSpec)
                .when()
                .delete("/2")
                .then()
                .spec(deleteUserResponseSpec);
    }

    @Test
    public void unSuccessfulLoginTest() {

        RequestUnsuccessfulLogin bodyRequest = new RequestUnsuccessfulLogin();
        bodyRequest.setEmail("peter@klaven");

        ResponseUnsuccessfulLogin response= given()
                .spec(unsuccessfulLoginRequestSpec)
                .body(bodyRequest)
                .when()
                .post()
                .then()
                .spec(unsuccessfulLoginResponseSpec)
                .extract().as(ResponseUnsuccessfulLogin.class);
        assertThat(response.getError().equals("Missing password"));
    }
}