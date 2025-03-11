import com.fasterxml.jackson.databind.ObjectMapper;

public class TransformJsonToString {

    public static String transform (String jsonResponse) {

        String jsonString = null;

        try {
            // Создаем ObjectMapper для преобразования JSON в объект
            ObjectMapper objectMapper = new ObjectMapper();
            DataPet pet = objectMapper.readValue(jsonResponse, DataPet.class); // Преобразуем JSON в объект Pet

            // Преобразование объекта обратно в строку более читаемый формат JSON
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pet);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonString;
    }
}


