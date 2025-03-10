import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static java.lang.Thread.sleep;


public class APITest {

    private PageSwagger api = new PageSwagger();

    @BeforeAll
    public static void deletion() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
        RemoveWrongPetIdpage.removeWrongPetIdPostRequest();
    }

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";

    }

    @Test
    public void wrongPetIdTest_BDD() {
        RemoveWrongPetIdpage.wrongPetIdGetRequest();
    }

    @Test
    @DisplayName("Проверяем часть функционала Swagger, а именно добавление, изменение и удаление")
    public void petTest_BDD() throws InterruptedException {
        try {
            api.petPostRequestNewPet(api.createPostRequestDemon());
            sleep(1000);
            api.checkNewPetGetRequest();
            sleep(1000);
            api.changeNamePetPostRequest();
            sleep(1000);
            api.checkInChangedGetRequest();
            sleep(1000);

        } catch (AssertionError ex) {
            ex.printStackTrace();
            throw ex;

        }finally {
            api.removePetPostRequest();
            sleep(1000);
            api.checkInRemovedPetGetRequest();
        }
    }
}
