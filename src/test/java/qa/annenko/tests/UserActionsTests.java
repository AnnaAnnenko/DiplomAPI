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
import static qa.annenko.specs.SingleUserNotFoundResponseSpec.singleUserNotFoundResponseSpec;
import static qa.annenko.specs.UnsuccessfulLoginRequestSpec.unsuccessfulLoginRequestSpec;
import static qa.annenko.specs.UnsuccessfulLoginResponseSpec.unsuccessfulLoginResponseSpec;
import static qa.annenko.specs.UpdateUserResponseSpec.updateUserResponseSpec;
import static qa.annenko.specs.UserListResponseSpec.userListResponseSpec;

public class UserActionsTests {

    @Test
    public void totalUserListTest() {
        ResponseUserList response = given()
                .spec(commonRequestSpec)
                .when()
                .get("/?page=2")
                .then()
                .spec(userListResponseSpec)
                .extract().as(ResponseUserList.class);
        assertEquals(response.getTotal(), 12);
    }

    @Test
    public void itemsUserListTest() {
        ResponseUserList response = given()
                .spec(commonRequestSpec)
                .when()
                .get("/?page=2")
                .then()
                .spec(userListResponseSpec)
                .extract().as(ResponseUserList.class);
        assertThat(response.getData().get(0).getLastName().equals("Lawson"));
        assertThat(response.getData().get(1).getLastName().equals("Ferguson"));
        assertThat(response.getData().get(2).getLastName().equals("Funke"));
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
                .post("/users")
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