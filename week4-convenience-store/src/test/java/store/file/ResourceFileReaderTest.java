package store.file;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResourceFileReaderTest {

    private final ResourceFileReader resourceFileReader = new ResourceFileReader();
    private final String products = "products.md";
    private final String promotions = "promotions.md";

    @Test
    @DisplayName("파일이 잘 불러와지나 확인")
    public void 파일_불러오기() {
        InputStream inputStream1 = ResourceFileReaderTest.class.getClassLoader()
                .getResourceAsStream(products);
        InputStream inputStream2 = ResourceFileReaderTest.class.getClassLoader()
                .getResourceAsStream(promotions);

        assertNotNull(inputStream1);
        assertNotNull(inputStream2);
    }

    @Test
    @DisplayName("파일 내용이 잘 출력되는지 확인")
    public void 파일_내용읽기() throws Exception {
        String expectedProducts = """
                name,price,quantity,promotion
                콜라,1000,10,탄산2+1
                콜라,1000,10,null
                사이다,1000,8,탄산2+1
                사이다,1000,7,null
                오렌지주스,1800,9,MD추천상품
                오렌지주스,1800,0,null
                탄산수,1200,5,탄산2+1
                탄산수,1200,0,null
                물,500,10,null
                비타민워터,1500,6,null
                감자칩,1500,5,반짝할인
                감자칩,1500,5,null
                초코바,1200,5,MD추천상품
                초코바,1200,5,null
                에너지바,2000,5,null
                정식도시락,6400,8,null
                컵라면,1700,1,MD추천상품
                컵라면,1700,10,null
                """;
        String expectedPromotions = """
                name,buy,get,start_date,end_date
                탄산2+1,2,1,2024-01-01,2024-12-31
                MD추천상품,1,1,2024-01-01,2024-12-31
                반짝할인,1,1,2024-11-01,2024-11-30
                """;
        String productsContent = resourceFileReader.readAllLines(products);
        String promotionsContent = resourceFileReader.readAllLines(promotions);

        assertTrue(expectedProducts.contentEquals(productsContent));
        assertTrue(expectedPromotions.contentEquals(promotionsContent));
    }
}





















