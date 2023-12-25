import client.UserCredentials;
import com.github.javafaker.Faker;
import constants.UserGenerator;
import data.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderData;
import order.OrderGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.OrderSteps;
import steps.UserSteps;

import java.util.ArrayList;

public class CreateOrderTest {
    private UserSteps userSteps;
    private UserData user;
    private OrderSteps orderSteps;
    private OrderData order;
    private String accessToken;
    ValidatableResponse response;
    private ArrayList<String> availableIds;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
        orderSteps = new OrderSteps();
        user = UserGenerator.getRandomUser();
        response = userSteps.createUser(user);
        accessToken = userSteps.getAccessToken(response, accessToken);
        availableIds = orderSteps.getAvailableIds();
    }

    @Test
    @DisplayName("Create order with authorization")
    @Description("Check create order with token and check answer")
    public void createOrderWithAuthorizationTest() {
        order = new OrderData(OrderGenerator.getCorrectOrderIds(availableIds, 1));
        response = orderSteps.createOrderWithToken(order, accessToken);
        orderSteps.checkAnswerSuccessCreatedOrder(response);
    }

    @Test
    @DisplayName("Create order without authorization")
    @Description("Check create order without token and check answer")
    public void createOrderWithoutAuthorizationTest() {
        order = new OrderData(OrderGenerator.getCorrectOrderIds(availableIds, 1));
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
        order = new OrderData(OrderGenerator.getWrongOrderIds(availableIds, 1));
        response = orderSteps.createOrderWithoutToken(order);
        orderSteps.checkAnswerWhenCreateOrderWithWrongHashIngredients(response);
    }

    @After
    public void deleteUser() {
        userSteps.deleteUser(accessToken);
    }
}
