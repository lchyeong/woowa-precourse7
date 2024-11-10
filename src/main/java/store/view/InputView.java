package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.entity.Product;

public class InputView {

    public static String promptPurchaseProducts() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return Console.readLine();
    }

    public static String promptMembershipDiscount() {
        System.out.println("\n멤버십 할인을 받으시겠습니까? (Y/N)");
        return Console.readLine();
    }

    public static String promptPurchaseOthers() {
        System.out.println("\n감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return Console.readLine();
    }

    public static String promptCheckPromotion(Product promotionProduct, int freeItemQuantity) {
        System.out.println(buildPromptCheckPromotion(promotionProduct, freeItemQuantity));
        return Console.readLine();
    }

    public static String promptSoldOutPromotion(Product promotionProduct, int freeItemQuantity) {
        System.out.println(buildPromptSoldOutPromotion(promotionProduct, freeItemQuantity));
        return Console.readLine();
    }

    private static StringBuilder buildPromptCheckPromotion(Product promotionProduct, int freeItemQuantity) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                String.format("현재 %s은(는) %,d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)",
                        promotionProduct.getName(),
                        freeItemQuantity
                ));
        return stringBuilder;
    }

    private static StringBuilder buildPromptSoldOutPromotion(Product promotionProduct, int freeItemQuantity) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                String.format("현재 %s %,d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)",
                        promotionProduct.getName(),
                        freeItemQuantity
                ));
        return stringBuilder;
    }
}
