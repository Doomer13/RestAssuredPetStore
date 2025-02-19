import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class APITest {

    private final int wrongPetId =12345;

    @BeforeEach
    public void setUp(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }

    @Test
    public void wrongPetIdTest_BDD (){
        given().when()
                .get(baseURI + "pet/" + wrongPetId)
                .then()
                .log().all()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found")
                .body("type",equalTo("error"));
    }
}
