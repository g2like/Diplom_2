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

public class CreateOrderTest {
    private UserSteps userSteps;
    private UserData user;
    private OrderSteps orderSteps;
    private OrderData order;
    private String accessToken;
    ValidatableResponse response;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
        orderSteps = new OrderSteps();
        user = RandomDataUser.getRandomUser();
        userSteps.createUser(user);
        response = userSteps.loginUser(UserCredentials.from(user));
        accessToken = userSteps.getAccessToken(response, accessToken);
    }

    @Test
    @DisplayName("Create order with authorization")
    @Description("Check create order with token and check answer")
    public void createOrderWithAuthorizationTest() {
        order = new OrderData(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa70", "61c0c5a71d1f82001bdaaa73"));
        response = orderSteps.createOrderWithToken(order, accessToken);
        orderSteps.checkAnswerSuccessCreatedOrder(response);
    }

    @Test
    @DisplayName("Create order without authorization")
    @Description("Check create order without token and check answer")
    public void createOrderWithoutAuthorizationTest() {
        order = new OrderData(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa70", "61c0c5a71d1f82001bdaaa73"));
        response = orderSteps.createOrderWithoutToken(order);
        orderSteps.checkAnswerSuccessCreatedOrder(response);
    }

    @Test
    @DisplayName("Create order without ingredients")
    @Description("Check create order without ingredients and check answer")
    public void createOrderWithoutIngredientsTest() {
        order = new OrderData();
        response = orderSteps.createOrderWithoutToken(order);
        orderSteps.checkAnswerWhenCreateOrderWithoutIngredients(response);
    }

    @Test
    @DisplayName("Create order with wrong has ingredients")
    @Description("Check create order with incorrect data for ingredients and check answer")
    public void createOrderWithWrongHashIngredientsTest() {
        order = new OrderData(List.of("sdasdasd", "sdasdasd", "sadasd"));
        response = orderSteps.createOrderWithoutToken(order);
        orderSteps.checkAnswerWhenCreateOrderWithWrongHashIngredients(response);
    }

    @After
    public void deleteUser() {
        userSteps.deleteUser(accessToken);
    }
}
