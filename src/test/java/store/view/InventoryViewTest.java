package store.view;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import store.product.entity.Product;
import store.promotion.entity.Promotion;

class InventoryViewTest {

    private final Promotion promotion = new Promotion(1L, "탄산2+1", 2, 1, "2024-11-10", "2024-11-15");
    private final Product product1 = new Product(1L, "콜라", 1000, 3, promotion);
    private final Product product2 = new Product(2L, "사이다", 3000, 10, null);

    private final InventoryView inventoryView = new InventoryView();


    @Test
    void showInventory() {
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        inventoryView.showInventory(products);
    }
}