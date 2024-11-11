package store.service;

import java.util.Map;
import java.util.Map.Entry;
import store.entity.Product;
import store.exception.ApiException;
import store.repository.ProductRepository;
import store.util.PurchaseProductParser;
import store.validator.DateValidator;
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

    private int membershipDiscount = 0;

    public void welcome() {
        InventoryView.showWelcomePhrase();
        InventoryView.showInventory(productRepository.findAll());

        String input = inputViewService.purchaseProduct();
        Map<String, Integer> parseInput = purchaseProductParser.parsePurchaseInput(input);

        try {
            parseInput = inputViewService.checkPromotion(parseInput);
        } catch (ApiException e) {
            welcome();
            return;
        }

        int totalCost = calculateTotalCost(parseInput);

        int promotionDiscount = calculatePromotionDiscount(parseInput);
        input = inputViewService.applyMembershipDiscount();
        if (input.equals("Y") || input.equals("y")) {
            membershipDiscount = membershipService.calculateMembershipDiscount(totalCost - promotionDiscount);
        }

        outputView.printReceipt(parseInput, totalCost, promotionDiscount, membershipDiscount);

        updateStock(parseInput);

        input = inputViewService.checkPurchaseOthers();
        if (input.equals("Y") || input.equals("y")) {
            welcome();
        }
    }

    public int calculateTotalCost(Map<String, Integer> parseInput) {
        int totalCost = 0;

        for (Entry<String, Integer> entry : parseInput.entrySet()) {
            String productName = entry.getKey();
            int purchaseQuantity = entry.getValue();

            Product product = productRepository.findProductByName(productName);

            totalCost += product.getPrice() * purchaseQuantity;
        }
        return totalCost;
    }

    public int calculatePromotionDiscount(Map<String, Integer> parseInput) {
        int promotionDiscount = 0;
        for (Entry<String, Integer> entry : parseInput.entrySet()) {
            String productName = entry.getKey();
            int purchaseQuantity = entry.getValue();
            int freeItems = 0;

            Product promotionProduct = productRepository.findPromotionProductByName(productName);

            if (promotionProduct == null || !DateValidator.checkPromotionDate(promotionProduct)) {
                continue;
            }
            int promoQuantity = productService.totalPurchaseItems(promotionProduct, promotionProduct.getQuantity());

            if (promoQuantity == 0) {
                continue;
            }

            if (purchaseQuantity > promotionProduct.getQuantity()) {
                freeItems = productService.getFreeItems(promotionProduct, promotionProduct.getQuantity());
            }
            if (purchaseQuantity < promotionProduct.getQuantity()) {
                freeItems = productService.getFreeItems(promotionProduct, purchaseQuantity);
            }

            promotionDiscount += freeItems * promotionProduct.getPrice();
        }
        return promotionDiscount;
    }

    public void updateStock(Map<String, Integer> parseInput) {
        for (Entry<String, Integer> entry : parseInput.entrySet()) {
            String productName = entry.getKey();
            int purchaseQuantity = entry.getValue();

            productService.getRemainQuantityAfterPromotion(productName, purchaseQuantity);
        }
    }
}
