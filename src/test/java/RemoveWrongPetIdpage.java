import io.restassured.http.ContentType;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RemoveWrongPetIdpage {


    private static final int wrongPetId = 1234567576;
    private static final String wrongApiKey = baseURI + "pet/" + wrongPetId;

    public static void removeWrongPetIdPostRequest() {
        given()
                .contentType(ContentType.URLENC)
                .formParam("api_key", wrongApiKey)
                .formParam("petId", wrongPetId)
                .when()
                .delete(baseURI + "pet/" + wrongPetId)
                .then()
                .statusCode(404);
    }

    public static void wrongPetIdGetRequest() {
        given().when()
                .get(baseURI + "pet/" + wrongPetId)
                .then()
                .log().all()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found")
                .body("type", equalTo("error"));
    }
}
