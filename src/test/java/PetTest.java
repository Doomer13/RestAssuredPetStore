import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class PetTest {
    private ApiClient api = new ApiClient();

    public void petTest()  {
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

