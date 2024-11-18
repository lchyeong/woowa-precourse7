package store.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.entity.Product;
import store.entity.Promotion;
import store.file.InternalFile;
import store.file.ResourceFileReader;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

class DataInitializerTest {
    private DataInitializer dataInitializer;
    private ResourceFileReader resourceFileReader = new ResourceFileReader();
    int productAmount = 18;
    int promotionAmount = 3;


    @BeforeEach
    @DisplayName("싱글톤 레포지토리 사전 초기화 작업")
    void repositorySetUp() {
        PromotionRepository promotionRepository = PromotionRepository.getInstance();
        ProductRepository productRepository = ProductRepository.getInstance();
        dataInitializer = new DataInitializer(productRepository, promotionRepository, resourceFileReader);

    }

    @Test
    @DisplayName("product.md 파일에서 제품 객체 리스트 생성 개수 확인")
    public void 제품_리스트업_테스트() {
        String resource = resourceFileReader.readAllLines(InternalFile.PRODUCTS.getFileName());
        List<Product> products = dataInitializer.loadProducts(resource);
        assertEquals(productAmount, products.size());
    }

    @Test
    @DisplayName("promotion.md 파일에서 제품 객체 리스트 생성 개수 확인")
    public void 프로모션_리스트업_테스트() {
        String resource = resourceFileReader.readAllLines(InternalFile.PROMOTIONS.getFileName());
        List<Promotion> promotions = dataInitializer.loadPromotions(resource);

        assertEquals(promotionAmount, promotions.size());
    }
}