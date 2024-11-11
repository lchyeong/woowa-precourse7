package store.view;

import java.util.Map;
import store.entity.Product;
import store.repository.ProductRepository;
import store.service.ProductService;
import store.validator.DateValidator;

public class OutputView {

    private final ProductRepository productRepository;
    private final ProductService productService;

    public OutputView(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    public void printReceipt(Map<String, Integer> parseInput, int totalCost, int promotionDiscount,
                             int membershipDiscount) {
        int totalQuantity = calculateTotalQuantity(parseInput);
        printReceiptStoreName();
        printPurchaseItems(parseInput);
        printFreeItems(parseInput);
        printReceiptFooter(totalCost, totalQuantity, promotionDiscount, membershipDiscount);
    }


    private void printReceiptStoreName() {
        System.out.println("\n============w 편의점==============");
        System.out.println("상품명\t\t수량\t금액");
    }

    private void printPurchaseItems(Map<String, Integer> parseInput) {
        for (Map.Entry<String, Integer> entry : parseInput.entrySet()) {
            String productName = entry.getKey();
            int purchaseQuantity = entry.getValue();
            Product product = productRepository.findProductByName(productName);

            if (product != null) {
                int productCost = product.getPrice() * purchaseQuantity;
                System.out.printf("%s\t\t%d\t%,d%n", productName, purchaseQuantity, productCost);
            }
        }
    }

    public void printFreeItems(Map<String, Integer> parseInput) {
        System.out.println("=============증    정===============");
        for (Map.Entry<String, Integer> entry : parseInput.entrySet()) {
            String productName = entry.getKey();
            int purchaseQuantity = entry.getValue();
            Product promotionProduct = productRepository.findPromotionProductByName(productName);
            if (promotionProduct == null || !DateValidator.checkPromotionDate(promotionProduct)) {
                continue;
            }

            int promoQuantity = productService.getFreeItems(promotionProduct, promotionProduct.getQuantity());

            if (promoQuantity == 0) {
                continue;
            }
            if (purchaseQuantity > promotionProduct.getQuantity()) {
                System.out.printf("%s\t\t%d%n", productName, promoQuantity);
                continue;
            }

            int freeItemQuantity = productService.getFreeItems(promotionProduct, purchaseQuantity);
            if (freeItemQuantity > 0) {
                System.out.printf("%s\t\t%d%n", productName, freeItemQuantity);
            }
        }
    }

    public void printReceiptFooter(int totalCost, int totalQuantity, int promotionDiscount, int membershipDiscount) {
        System.out.println("====================================");
        System.out.printf("총구매액\t\t%,d\t%,d%n", totalQuantity, totalCost);
        System.out.printf("행사할인\t\t\t-%,d%n", promotionDiscount);
        System.out.printf("멤버십할인\t\t\t-%,d%n", membershipDiscount);
        System.out.printf("내실돈\t\t\t%,d%n", totalCost - promotionDiscount - membershipDiscount);
    }

    private int calculateTotalQuantity(Map<String, Integer> parseInput) {
        int totalQuantity = 0;
        for (Map.Entry<String, Integer> entry : parseInput.entrySet()) {
            int purchaseQuantity = entry.getValue();
            totalQuantity += purchaseQuantity;
        }
        return totalQuantity;
    }
}
