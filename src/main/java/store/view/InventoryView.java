package store.view;

import java.util.List;
import store.product.entity.Product;

public class InventoryView {

    public void showWelcomePhrase() {
        System.out.println("안녕하세요. 우아24시 편의점입니다.\n현재 보유하고 있는 상품입니다.\n");
    }

    public void showInventory(List<Product> products) {
        StringBuilder sb = new StringBuilder();
        for (Product product : products) {
            sb.append(
                    String.format("- %s %,d원 %,d개 %s%n",
                            product.getName(),
                            product.getPrice(),
                            product.getQuantity(),
                            product.getPromotion())
            );
        }
        System.out.println(sb);
    }
}
