import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class PageSwagger {

    Integer idAnt = 13;
    String nameAnt = "The gold-digging ant";
    String statusAnt = "sold";

    Integer id = 666;
    String name = "Demon";
    String name1 = "CyberDemon";
    String status = "available";
    String status1 = "sold";

    String apiKey = baseURI + "pet/" + id;


    @DisplayName("Метод для создания тела The gold-digging ant для post запроса")
    public Map<String, String> createPostRequestTheGoldDiggingAnt(Object id, String name, String status) {

        Map<String, String> requestAnt = new HashMap<>();
        requestAnt.put("id", idAnt.toString());
        requestAnt.put("name", nameAnt);
        requestAnt.put("status", statusAnt);
        return requestAnt;
    }

    @DisplayName("Метод для создания тела Demon для post запроса")
    public Map<String, String> createPostRequestDemon() {

        Map<String, String> requestDemon = new HashMap<>();
        requestDemon.put("id", id.toString());
        requestDemon.put("name", name);
        requestDemon.put("status", status);
        return requestDemon;
    }

    public void petPostRequestNewPet(Map<String, String> requestBody) {
        given().contentType("application/json")
                .body(requestBody)
                .when()
                .post(baseURI + "pet/")
                .then()
                .time(lessThan(2000L))
                .assertThat()
                .statusCode(200);
    }

    public void checkNewPetGetRequest() {
        given().when()
                .get(baseURI + "pet/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo("Demon"))
                .body("status", equalTo("available"))
                .body("id", equalTo(id));
    }

    public void changeNamePetPostRequest() {
        given()
                .contentType(ContentType.URLENC)
                .formParam("name", name1)
                .formParam("status", status1)
                .when()
                .post(baseURI + "pet/" + id)
                .then()
                .statusCode(200);
    }

    public void checkInChangedGetRequest() {
        given().when()
                .get(baseURI + "pet/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("CyberDemon"))
                .body("status", equalTo("sold"));
    }

    public void removePetPostRequest() {
        given()
                .contentType(ContentType.URLENC)
                .formParam("api_key", apiKey)
                .formParam("petId", id)
                .when()
                .delete(baseURI + "pet/" + id)
                .then()
                .statusCode(200);
    }

    public void checkInRemovedPetGetRequest() {
        given().when()
                .get(apiKey)
                .then()
                .log().all()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found")
                .body("type", equalTo("error"),
                        "message", equalTo("Pet not found"));
    }

}
