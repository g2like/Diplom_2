package steps;
import client.UserCredentials;
import constants.Urls;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import data.UserData;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class UserSteps extends RestClient {
    UserSteps userSteps;

    @Step("Create user")
    public ValidatableResponse createUser(UserData user) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(user)
                .post(Urls.AUTH_REGISTER_USER_URL)
                .then();
    }

    @Step("Delete user")
    public ValidatableResponse deleteUser(String accessToken) {
        if (accessToken != null) {
            return given()
                    .header("authorization", accessToken)
                    .spec(requestSpecification())
                    .when()
                    .delete(Urls.DELETE_USER_URL)
                    .then();
        } else {
            return given()
                    .spec(requestSpecification())
                    .when()
                    .delete(Urls.AUTH_USER_URL)
                    .then();
        }

    }

    @Step("Login user")
    public ValidatableResponse loginUser(UserCredentials user) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(user)
                .post(Urls.LOGIN_USER_URL)
                .then();
    }

    @Step("Authorization with token")
    public ValidatableResponse authorizationWithToken(String accessToken, UserData user) {
        return given()
                .header("authorization", accessToken)
                .spec(requestSpecification())
                .and()
                .body(user)
                .when()
                .patch(Urls.AUTH_USER_URL)
                .then();
    }

    @Step("Authorization without token")
    public ValidatableResponse authorizationWithoutToken(UserData user) {
        return given()
                .spec(requestSpecification())
                .body(user)
                .when()
                .patch(Urls.AUTH_USER_URL)
                .then();
    }

    @Step("Get access token")
    public String getAccessToken(ValidatableResponse response, String accessToken) {
        return accessToken = response.extract().body().path("accessToken").toString();
    }

    @Step("Check success answer")
    public void checkSuccessAnswer(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(true))
                .statusCode(200);
    }

    @Step("Check answer when such user already was created")
    public void checkAnswerWhenSuchUserAlreadyWasCreated(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("message", is("User already exists"));
    }

    @Step("Check answer when not fill field")
    public void checkAnswerWhenNotFillField(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("message", is("Email, password and name are required fields"));
    }

    @Step("Check answer when password or email incorrect")
    public void checkAnswerWhenPasswordOrEmailIncorrect(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", is("email or password are incorrect"));
    }

    @Step("Change data")
    public void changeUserData(UserData user) {
        user.setName(user.getName() + "1");
        user.setPassword(user.getPassword() + "1");
        user.setEmail(user.getEmail() + "1");
    }

    @Step("Check answer when change data without authorization")
    public void checkAnswerWhenChangeDataWithoutAuthorization(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", is("You should be authorised"));

    }
}
