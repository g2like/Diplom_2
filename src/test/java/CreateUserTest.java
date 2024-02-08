import constants.UserGenerator;
import data.UserData;
import groovy.xml.StreamingDOMBuilder;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.UserSteps;

public class CreateUserTest {
    private UserData user;
    private ValidatableResponse response;
    private UserSteps userSteps;
    private String accessToken;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
        user = UserGenerator.getRandomUser();
    }

    @Test
    @DisplayName("Create user")
    @Description("Check create user and check answer")
    public void createUserTest() {
        response = userSteps.createUser(user);
        accessToken = userSteps.getAccessToken(response, accessToken);
        userSteps.checkSuccessAnswer(response);
    }

    @Test
    @DisplayName("Create user with the same data")
    @Description("Check create user with the same data and check answer")
    public void createUserAlreadyHaveTest() {
        response = userSteps.createUser(user);
        response = userSteps.createUser(user);
        userSteps.checkAnswerWhenSuchUserAlreadyWasCreated(response);
    }

    @Test
    @DisplayName("Create user without email filed")
    @Description("Check create user when email field empty and check answer")
    public void createUserWithoutFieldEmail() {
        user.setEmail("");
        response = userSteps.createUser(user);
        userSteps.checkAnswerWhenNotFillField(response);
    }

    @Test
    @DisplayName("Create user without password filed")
    @Description("Check create user when password field empty and check answer")
    public void createUserWithoutFieldPassword() {
        user.setPassword("");
        response = userSteps.createUser(user);
        userSteps.checkAnswerWhenNotFillField(response);
    }

    @Test
    @DisplayName("Create user without name filed")
    @Description("Check create user when name field empty and check answer")
    public void createUserWithoutFieldName() {
        user.setName("");
        response = userSteps.createUser(user);
        userSteps.checkAnswerWhenNotFillField(response);
    }

    @After
    public void deleteUser() {
        userSteps.deleteUser(accessToken);
    }

}
