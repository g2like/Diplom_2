import client.UserCredentials;
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
import java.util.List;

public class GetListOfOrdersUserTest {
    private UserData user;
    private UserSteps userSteps;
    private OrderData order;
    private OrderSteps orderSteps;
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
        order = new OrderData(OrderGenerator.getCorrectOrderIds(availableIds, 1));
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
