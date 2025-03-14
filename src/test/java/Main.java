import io.restassured.RestAssured;

public class Main {
    static PetTest start =new PetTest();

    public static void main(String[] args) throws InterruptedException {

        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
        start.petTest();

    }
}
