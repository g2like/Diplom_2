import client.UserCredentials;
import constants.RandomDataUser;
import data.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.UserSteps;

public class ChangeDataUserTest {

    private ValidatableResponse response;
    private UserSteps userSteps;
    private UserData user;
    private String accessToken;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
        user = RandomDataUser.getRandomUser();
        response = userSteps.createUser(user);
        accessToken = response.extract().body().path("accessToken").toString();
        response = userSteps.loginUser(UserCredentials.from(user));
    }

    @Test
    @DisplayName("Change data with authorization")
    @Description("Check authorization when change data with token and check answer")
    public void ChangeDataWithAuthorizationTest() {
        userSteps.changeUserData(user);
        response = userSteps.authorizationWithToken(accessToken, user);
        userSteps.checkSuccessAnswer(response);
    }

    @Test
    @DisplayName("Change data without authorization")
    @Description("Check authorization when change data without token and check answer")
    public void ChangeDataWithoutAuthorizationTest() {
        userSteps.changeUserData(user);
        response = userSteps.authorizationWithoutToken(user);
        userSteps.checkAnswerWhenChangeDataWithoutAuthorization(response);
    }

    @After
    public void deleteUser() {
        userSteps.deleteUser(accessToken);
    }
}
