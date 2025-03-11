import io.restassured.http.ContentType;

import java.io.IOException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;

public class ApiClient {

    private  String demonfilePath = "src/test/java/jsonText/DemonPet.json";
    private  String cyberDemonfilePath = "src/test/java/jsonText/CyberDemonPet.json";


    JsonFileReader json =new JsonFileReader(null);

    String apiKey = baseURI + "pet/" + "666";
    int id = 666;// это костыль как сделать чтобы оптимизировать, чтобы пр сама снала какой айди?


    public void petPostRequestNewPet() {
        try {
            String requestBody = json.readJson(demonfilePath);
            given().contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post(baseURI + "pet/")
                    .then()
                    .time(lessThan(2000L))
                    .assertThat()
                    .statusCode(200);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении JSON-файла: " + e.getMessage());
        }
    }

    // Метод для проверки нового питомца
    public void checkNewPetGetRequest() {
        // Выполняем GET-запрос
        ResponseCreatPet jsonResponse = given()
                .when()
                .get(baseURI + "pet/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(ResponseCreatPet.class);
        System.out.println("Полученный питомец:\n" + "id: " + jsonResponse.getId()
        +", name: " + jsonResponse.getName() + ", status: " + jsonResponse.getStatus());
    }

    public void changeNamePetPostRequest() {
        try {
            String requestBody = json.readJson(cyberDemonfilePath);
            String responseBody = given().contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post(baseURI + "pet/")
                    .then()
                    .time(lessThan(2000L))
                    .statusCode(200)
                    .extract().asString();

            assertThat("Имя не поменялось", responseBody, containsString("\"name\":\"CyberDemon\""));

        } catch (IOException e) {
            System.err.println("Ошибка при чтении JSON-файла: " + e.getMessage());
        }
    }

    public void checkInChangedGetRequest() {
        ResponseCreatPet jsonResponse = given()
                .when()
                .get(baseURI + "pet/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(ResponseCreatPet.class);
        System.out.println("Полученный питомец:\n" + "id: " + jsonResponse.getId()
                +", name: " + jsonResponse.getName() + ", status: " + jsonResponse.getStatus());
    }

    public void removePetPostRequest() {
        given()
                .contentType(ContentType.URLENC)
                .formParam("api_key", apiKey)
                .formParam("petId", id)
                .when()
                .delete(baseURI + "pet/" + id)
                .then()
                .log().all()
                .statusCode(200);
    }

    public void checkInRemovedPetGetRequest() {
        String jsonResponse = given()
                .when()
                .get(baseURI + "pet/" + id)
                .then()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found")
                .extract().asString();
    }
}
