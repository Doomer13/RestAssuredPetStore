import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

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

    @Test
    public void newPetTest(){
        Map<String,String> request = new HashMap<>();
        Integer id =13 ;
        String name = "The gold-digging ant";
        String status = "sold";
        request.put("id", id.toString());
        request.put("name", name);
        request.put("status", status);
        given().contentType("application/json")
                .body(request)
                .when()
                .post(baseURI+"pet/")
                .then()
                .log().all()
                .time(lessThan(2000L))
                .assertThat()
                .statusCode(200)
                .body("id",equalTo(id))
                .body("name",equalTo(name))
                .body("status",equalTo(status));

    }

    @Test
    @DisplayName("Проверяем часть функционала Swagger, а именно добавление, изменение иудаление")
    public void petTest_BDD(){

       //переменные
        String name1 = "CyberDemon";
        String status2 = "sold";

        Map<String,String> request = new HashMap<>();
        Integer id =666 ;
        String name = "Demon";
        String status = "available";
        request.put("id", id.toString());
        request.put("name", name);
        request.put("status", status);

        String apiKey = baseURI + "pet/" + id;

        //Отправляем post запрос на создание нового питомца
        given().contentType("application/json")
                .body(request)
                .when()
                .post(baseURI+"pet/")
                .then()
                .time(lessThan(2000L))
                .assertThat()
                .statusCode(200);

        //Проверяем get запросом по id о наличии питомца в базе данных сервера
        given().when()
                .get(baseURI + "pet/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .body("name",equalTo("Demon"))
                .body("status",equalTo("available"));

        //меняем данные нашего питомца post запросом
        given()
                .contentType(ContentType.URLENC)
                .formParam("name", name1)
                .formParam("status", status2)
                .when()
                .post(baseURI + "pet/" + id) // Укажите путь
                .then()
                .statusCode(200);

        //делаем get запрос и проверяем изменения
        given().when()
                .get(baseURI + "pet/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .body("name",equalTo("CyberDemon"))
                .body("status",equalTo("sold"));

        //Удоляем питомца post запрос
        given()
                .contentType(ContentType.URLENC)
                .formParam("api_key", apiKey)
                .formParam("petId", id)
                .when()
                .post(baseURI + "pet/" + id)
                .then()
                .statusCode(200);

        //Делаем Get запрос и провреям удаление питомца
        given().when()
                .get(apiKey)
                .then()
                .log().all()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found")
                .body("type",equalTo("error"));
    }
}
