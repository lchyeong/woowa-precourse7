package store.view;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import store.entity.Product;
import store.entity.Promotion;
import store.util.DateUtil;

class InventoryViewTest {

    private final Promotion promotion = new Promotion("탄산2+1", 2, 1, DateUtil.toDate("2024-11-10"),
            DateUtil.toDate("2024-11-15"));
    private final Product product1 = new Product("콜라", 1000, 3, promotion);
    private final Product product2 = new Product("사이다", 3000, 10, null);

    private final InventoryView inventoryView = new InventoryView();


    @Test
    void showInventory() {
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        inventoryView.showInventory(products);
    }
}