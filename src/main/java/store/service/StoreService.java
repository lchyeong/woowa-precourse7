package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import store.entity.Product;
import store.repository.ProductRepository;
import store.util.PurchaseProductParser;
import store.validator.PurchaseValidator;
import store.view.InventoryView;
import store.view.OutputView;

public class StoreService {

    private final InputViewService inputViewService;
    private final ProductRepository productRepository;
    private final PurchaseValidator purchaseValidator;
    private final ProductService productService;
    private final MembershipService membershipService;
    private final PurchaseProductParser purchaseProductParser;
    private final OutputView outputView;

    public StoreService(InputViewService inputViewService, ProductRepository productRepository,
                        PurchaseValidator purchaseValidator, ProductService productService,
                        MembershipService membershipService,
                        PurchaseProductParser purchaseProductParser, OutputView outputView) {
        this.inputViewService = inputViewService;
        this.productRepository = productRepository;
        this.purchaseValidator = purchaseValidator;
        this.productService = productService;
        this.membershipService = membershipService;
        this.purchaseProductParser = purchaseProductParser;
        this.outputView = outputView;
    }

    private int membershipDiscount;

    public void welcome() {
        InventoryView.showWelcomePhrase();
        InventoryView.showInventory(productRepository.findAll());

        String input = inputViewService.purchaseProduct();
        System.out.println(input);
        Map<String, Integer> parseInput = purchaseProductParser.parsePurchaseInput(input);

        input = inputViewService.checkPromotion(parseInput);
        int totalCost = calculateTotalCost(parseInput);

        int promotionDiscount = calculatePromotionDiscount(parseInput);
        input = inputViewService.applyMembershipDiscount();
        if (input.equals("Y") || input.equals("y")) {
            membershipDiscount = membershipService.calculateMembershipDiscount(totalCost - promotionDiscount);
        }
        outputView.printReceipt(parseInput, totalCost, promotionDiscount, membershipDiscount);

        input = inputViewService.checkPurchaseOthers();
        if (input.equals("Y") || input.equals("y")) {
            welcome();
        }

    }

    public int calculateTotalCost(Map<String, Integer> parseInput) {
        int totalCost = 0;

        for (Map.Entry<String, Integer> entry : parseInput.entrySet()) {
            String productName = entry.getKey();
            int purchaseQuantity = entry.getValue();

            Product product = productRepository.findProductByName(productName);

            totalCost += product.getPrice() * purchaseQuantity;
        }
        return totalCost;
    }

    public int calculatePromotionDiscount(Map<String, Integer> parseInput) {
        int promotionDiscount = 0;
        LocalDateTime now = DateTimes.now();
        LocalDate currentDate = now.toLocalDate();
        for (Map.Entry<String, Integer> entry : parseInput.entrySet()) {
            String productName = entry.getKey();
            int purchaseQuantity = entry.getValue();

            Product promotionProduct = productRepository.findPromotionProductByName(productName);
            if (promotionProduct == null) {
                continue;
            }
            LocalDate startDate = promotionProduct.getPromotion().getStartDate().toLocalDate();
            LocalDate endDate = promotionProduct.getPromotion().getEndDate().toLocalDate();

            if (currentDate.isBefore(startDate) || currentDate.isAfter(endDate)) {
                continue;
            }
            if (promotionProduct != null) {
                int freeItems = productService.getAdditionalFreeItems(promotionProduct, purchaseQuantity);
                promotionDiscount += freeItems * promotionProduct.getPrice();
            }
        }
        return promotionDiscount;
    }
}
