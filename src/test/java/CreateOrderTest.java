import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final String[] changeColor;

    public CreateOrderTest(String[] changeColor) {
        this.changeColor = changeColor;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Parameterized.Parameters
    public static Object[][] getQuest() {


        return new Object[][]{
                {new String[]{"GRAY"}},
                {new String[]{"BLACK"}},
                {new String[]{"GRAY", "BLACK"}},
                {new String[]{""}},
        };

    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrder() {
        SetupOrderList setupOrderList = new SetupOrderList("Sergey", "Smirnov", "Moscow, Limonnaya st.3 apt.1", "4",
                "+7 925 091 23 44", 2, "2022-12-12", "call an hour before delivery ", changeColor);
        given()
                .header("Content-type", "application/json")
                .body(setupOrderList)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }
}