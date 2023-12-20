import client.UserCredentials;
import constants.RandomDataUser;
import data.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.OrderSteps;
import steps.UserSteps;

import java.util.List;

public class GetListOfOrdersUserTest {
    private UserData user;
    private UserSteps userSteps;
    private OrderData order;
    private OrderSteps orderSteps;
    private String accessToken;
    ValidatableResponse response;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
        orderSteps = new OrderSteps();
        user = RandomDataUser.getRandomUser();
        userSteps.createUser(user);
        response = userSteps.loginUser(UserCredentials.from(user));
        accessToken = response.extract().body().path("accessToken").toString();
        order = new OrderData(List.of("61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa79", "61c0c5a71d1f82001bdaaa78"));
        orderSteps.createOrderWithToken(order, accessToken);
    }

    @Test
    @DisplayName("Get list of orders with token")
    @Description("Get list of orders with token and check answer")
    public void getListOfOrdersWithTokenTest() {
        response = orderSteps.getListOfOrdersWithToken(accessToken);
        orderSteps.checkAnswerWhenGetOrderWithToken(response);
    }

    @Test
    @DisplayName("Get list of orders without token")
    @Description("Get list of orders without token and check answer")
    public void getListOfOrdersWithoutTokenTest() {
        response = orderSteps.getListOfOrdersWithoutToken();
        orderSteps.checkAnswerWhenGetOrderWithoutToken(response);
    }

    @After
    public void deleteUser() {
        userSteps.deleteUser(accessToken);
    }
}
