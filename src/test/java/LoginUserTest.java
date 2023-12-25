import client.UserCredentials;
import constants.UserGenerator;
import data.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.UserSteps;

public class LoginUserTest {
    private UserData user;
    private ValidatableResponse response;
    private UserSteps userSteps;
    private String accessToken;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
        user = UserGenerator.getRandomUser();
        response = userSteps.createUser(user);
        accessToken = userSteps.getAccessToken(response,accessToken);
    }

    @Test
    @DisplayName("Login with already have user")
    @Description("Login with already have user and check answer")
    public void loginWithAlreadyHaveUser() {
        response = userSteps.loginUser(UserCredentials.from(user));
        userSteps.checkSuccessAnswer(response);
    }

    @Test
    @DisplayName("Login with wrong email")
    @Description("Login with wrong email and check answer")
    public void loginWithWrongEmail() {
        user.setEmail("sssdasda@yandex.ru");
        response = userSteps.loginUser(UserCredentials.from(user));
        userSteps.checkAnswerWhenPasswordOrEmailIncorrect(response);
    }

    @Test
    @DisplayName("Login with wrong password")
    @Description("Login with wrong password and check answer")
    public void loginWithWrongPassword() {
        user.setPassword("random");
        response = userSteps.loginUser(UserCredentials.from(user));
        userSteps.checkAnswerWhenPasswordOrEmailIncorrect(response);
    }

    @After
    public void deleteUser() {
        userSteps.deleteUser(accessToken);
    }
}
