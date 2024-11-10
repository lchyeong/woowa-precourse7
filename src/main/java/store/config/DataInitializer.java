package store.config;

import static store.util.FileUtil.addKeysFromResource;
import static store.util.FileUtil.addValuesToKeys;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import store.constants.Delimiter;
import store.entity.Product;
import store.entity.Promotion;
import store.file.InternalFile;
import store.file.ResourceFileReader;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

public class DataInitializer {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final ResourceFileReader resourceFileReader;

    public DataInitializer(ProductRepository productRepository, PromotionRepository promotionRepository,
                           ResourceFileReader resourceFileReader) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
        this.resourceFileReader = resourceFileReader;
    }


    public void init() {
        String product = resourceFileReader.readAllLines(InternalFile.PRODUCTS.getFileName());
        String promotion = resourceFileReader.readAllLines(InternalFile.PROMOTIONS.getFileName());

        List<Promotion> promotions = loadPromotions(promotion);
        promotionRepository.init(promotions);

        List<Product> products = loadProducts(product);
        productRepository.init(products);

    }

    public List<Product> loadProducts(String resource) {
        Map<String, String> product;
        List<String> resourceAllLines = Delimiter.LINE_FEED.splitDelimiter(resource);
        Map<String, String> columns = addKeysFromResource(resourceAllLines.getFirst());
        List<Product> products = new LinkedList<>();

        for (int i = 1; i < resourceAllLines.size(); i++) {
            product = addValuesToKeys(columns, resourceAllLines.get(i));
            Promotion promotion = promotionRepository.findPromotionWithName(product.get("promotion"));
            products.add(new Product(product, promotion));
        }
        return products;
    }

    public List<Promotion> loadPromotions(String resource) {
        Map<String, String> promotion;
        List<String> resourceAllLines = Delimiter.LINE_FEED.splitDelimiter(resource);
        Map<String, String> columns = addKeysFromResource(resourceAllLines.getFirst());
        List<Promotion> promotions = new LinkedList<>();

        for (int i = 1; i < resourceAllLines.size(); i++) {
            promotion = addValuesToKeys(columns, resourceAllLines.get(i));
            promotions.add(new Promotion(promotion));
        }
        return promotions;
    }
}
