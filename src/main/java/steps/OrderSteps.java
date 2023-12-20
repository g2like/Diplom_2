package steps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import constants.Urls;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import order.OrderData;

public class OrderSteps extends RestClient {
    @Step("Create order with token")
    public ValidatableResponse createOrderWithToken(OrderData order, String accessToken) {
        return given()
                .header("authorization", accessToken)
                .spec(requestSpecification())
                .body(order)
                .when()
                .post(Urls.ORDER_URL)
                .then();
    }

    @Step("Create order without token")
    public ValidatableResponse createOrderWithoutToken(OrderData order) {
        return given()
                .spec(requestSpecification())
                .body(order)
                .when()
                .post(Urls.ORDER_URL)
                .then();
    }

    @Step("Get list of orders with token")
    public ValidatableResponse getListOfOrdersWithToken(String accessToken) {
        return given()
                .header("authorization", accessToken)
                .spec(requestSpecification())
                .when()
                .get(Urls.ORDER_URL)
                .then();
    }

    @Step("Get list of orders without token")
    public ValidatableResponse getListOfOrdersWithoutToken() {
        return given()
                .spec(requestSpecification())
                .when()
                .get(Urls.ORDER_URL)
                .then();
    }

    @Step("Check answer success create order")
    public void checkAnswerSuccessCreatedOrder(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(true))
                .statusCode(200);
    }

    @Step("Check answer when create order without ingredients")
    public void checkAnswerWhenCreateOrderWithoutIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", is("Ingredient ids must be provided"));
    }

    @Step("Check answer when create order with wrong hash ingredients")
    public void checkAnswerWhenCreateOrderWithWrongHashIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(500);
    }

    @Step("Check answer when get order with token")
    public void checkAnswerWhenGetOrderWithToken(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(true))
                .statusCode(200);
    }

    @Step("Check answer when get order without token")
    public void checkAnswerWhenGetOrderWithoutToken(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", is("You should be authorised"));
    }
}
