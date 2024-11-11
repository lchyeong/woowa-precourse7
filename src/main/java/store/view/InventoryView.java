package store.view;

import java.util.List;
import store.entity.Product;

public class InventoryView {

    public static void showWelcomePhrase() {
        System.out.println("안녕하세요. w편의점입니다.\n현재 보유하고 있는 상품입니다.\n");
    }

    public static void showInventory(List<Product> products) {
        StringBuilder sb = new StringBuilder();
        for (Product product : products) {
            sb.append(
                    String.format("- %s %,d원 %s %s%n",
                            product.getName(),
                            product.getPrice(),
                            checkQuantity(product.getQuantity()),
                            product.getPromotionName())
            );
        }
        System.out.println(sb);
    }

    private static String checkQuantity(int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        return quantity + "개";
    }
}
