package store.file;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResourceFileReaderTest {
    private final String products = "products.md";
    private final String promotions = "promotions.md";

    private final InputStream inputStream1 = ResourceFileReaderTest.class.getClassLoader()
            .getResourceAsStream(products);
    private final InputStream inputStream2 = ResourceFileReaderTest.class.getClassLoader()
            .getResourceAsStream(promotions);

    private final BufferedReader reader1 = new BufferedReader(new InputStreamReader(inputStream1));
    private final BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputStream2));

    @Test
    @DisplayName("파일이 잘 불러와지나 확인")
    public void 파일_불러오기() {
        assertNotNull(inputStream1);
        assertNotNull(inputStream2);
    }

    @Test
    @DisplayName("파일 내용이 잘 출력되는지 확인")
    public void 파일_내용읽기() throws Exception {

        StringBuilder productsContent = readFile(reader1);
        StringBuilder promotionsContent = readFile(reader2);

        assertFalse(productsContent.isEmpty());
        assertFalse(promotionsContent.isEmpty());
    }

    private StringBuilder readFile(BufferedReader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder;
    }
}





















