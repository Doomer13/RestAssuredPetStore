import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFileReader {

    public String filePath;

    public JsonFileReader(String filePath) {
        this.filePath = filePath;
    }

    public String readJson(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}

