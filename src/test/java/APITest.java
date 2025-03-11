import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.*;


public class APITest {

    private ApiClient api = new ApiClient();
    private DataPet pet =new DataPet();


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
    @DisplayName("Проверяем часть функционала Swagger, а именно добавление, изменение и удаление")
    public void petTest_BDD() throws InterruptedException {

        try {

            api.petPostRequestNewPet();

            await().atMost(5, SECONDS).untilAsserted(() -> {
                api.checkNewPetGetRequest();
            });

            api.changeNamePetPostRequest();

            await().atMost(5, SECONDS).untilAsserted(() -> {
                api.checkInChangedGetRequest();
            });

        } catch (AssertionError ex) {
            ex.printStackTrace();
            throw ex;

        }finally {
            api.removePetPostRequest();

            await().atMost(5, SECONDS).untilAsserted(() -> {
                api.checkInRemovedPetGetRequest();
            });
        }
    }
}
