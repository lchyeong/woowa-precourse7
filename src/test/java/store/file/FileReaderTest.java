package store.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    private final String Delimiter = ",";
    private final String products = "products.md";
    private final String promotions = "promotions.md";

    private final InputStream inputStream1 = FileReaderTest.class.getClassLoader().getResourceAsStream(products);
    private final InputStream inputStream2 = FileReaderTest.class.getClassLoader().getResourceAsStream(promotions);

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

    @Test
    @DisplayName("첫줄을 읽어와 필드로 키값으로 만들기")
    public void 데이터_구조화() throws Exception {

        String productsKey = reader1.readLine();
        String promotionsKey = reader2.readLine();

        List<String> key1 = splitWithDelimiter(productsKey);
        List<String> key2 = splitWithDelimiter(promotionsKey);
        Map<String, String> productsMap = addKeyInMap(key1);
        Map<String, String> promotionsMap = addKeyInMap(key2);

        List<String> expectedProductsKeys = Arrays.asList("name", "price", "quantity", "promotion");
        List<String> expectedPromotionsKeys = Arrays.asList("name", "buy", "get", "start_date", "end_date");

        List<String> actualProductsKeys = new ArrayList<>(productsMap.keySet());
        List<String> actualPromotionsKeys = new ArrayList<>(promotionsMap.keySet());
        //순서 보장이 의미가 있을까?
        assertEquals(expectedProductsKeys, actualProductsKeys);
        assertEquals(expectedPromotionsKeys, actualPromotionsKeys);
    }

    private StringBuilder readFile(BufferedReader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder;
    }

    private List<String> splitWithDelimiter(String input) {
        return Arrays.asList(input.split(Delimiter));
    }

    //오버로딩 해서 밸류도 넣어줘도 되지 않을까..
    private Map<String, String> addKeyInMap(List<String> keys) {
        Map<String, String> result = new LinkedHashMap<>();

        for (String key : keys) {
            result.put(key, "");
        }
        return result;
    }
}





















