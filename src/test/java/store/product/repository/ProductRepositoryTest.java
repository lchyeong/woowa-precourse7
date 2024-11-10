package store.product.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.config.DataInitializer;
import store.file.ResourceFileReader;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

class ProductRepositoryTest {

    private final ResourceFileReader resourceFileReader = new ResourceFileReader();
    private ProductRepository productRepository;
    private PromotionRepository promotionRepository;

    @BeforeEach
    @DisplayName("레포지토리 사전 초기화 작업")
    void repositorySetUp() {
        promotionRepository = PromotionRepository.getInstance();
        productRepository = ProductRepository.getInstance();
        DataInitializer dataInitializer = new DataInitializer(productRepository, promotionRepository,
                resourceFileReader);
        dataInitializer.init();
    }

    @Test
    void findAll() {
        assertEquals(16, productRepository.findAll().size());
    }

    @Test
    void findQuantityByName() {
        String name = "콜라";
        int expected = 20;
        int result = productRepository.findQuantityByName(name);

        assertEquals(expected, result);
    }
}