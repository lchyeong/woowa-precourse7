package store.service;

import java.util.Map;
import store.entity.Product;
import store.exception.ApiException;
import store.repository.ProductRepository;
import store.validator.DateValidator;
import store.validator.PurchaseValidator;
import store.validator.YesNoValidator;
import store.view.InputView;

public class InputViewService {

    private final PurchaseValidator purchaseValidator;
    private final ProductRepository productRepository;
    private final YesNoValidator yesNoValidator;
    private final ProductService productService;
    private boolean pass;
    private String input;

    public InputViewService(PurchaseValidator purchaseValidator, ProductRepository productRepository,
                            YesNoValidator yesNoValidator, ProductService productService) {
        this.purchaseValidator = purchaseValidator;
        this.productRepository = productRepository;
        this.yesNoValidator = yesNoValidator;
        this.productService = productService;
    }

    public String purchaseProduct() {
        do {
            try {
                input = InputView.promptPurchaseProducts();
                pass = purchaseValidator.isEmptyInput(input);
                pass = purchaseValidator.validatePurchasePattern(input);
                pass = purchaseValidator.checkPurchaseProductNameAndAmount(input);
            } catch (ApiException e) {
                System.out.println(e.getMessage());
                pass = false;
            }
        } while (!pass);
        return input;
    }

    public String applyMembershipDiscount() {
        do {
            try {
                input = InputView.promptMembershipDiscount();
                pass = purchaseValidator.isEmptyInput(input);
                pass = yesNoValidator.isYesOrNo(input);
            } catch (ApiException e) {
                System.out.println(e.getMessage());
                pass = false;
            }
        } while (!pass);
        return input;
    }

    public String checkPurchaseOthers() {
        do {
            try {
                input = InputView.promptPurchaseOthers();
                pass = purchaseValidator.isEmptyInput(input);
                pass = yesNoValidator.isYesOrNo(input);
            } catch (ApiException e) {
                System.out.println(e.getMessage());
                pass = false;
            }
        } while (!pass);
        return input;
    }

    public Map<String, Integer> checkPromotion(Map<String, Integer> parseInput) {
        for (Map.Entry<String, Integer> entry : parseInput.entrySet()) {
            String productName = entry.getKey();
            int purchaseQuantity = entry.getValue();

            Product promotionProduct = productRepository.findPromotionProductByName(productName);
            if (promotionProduct == null || !DateValidator.checkPromotionDate(promotionProduct)
                    || promotionProduct.getPromotion() == null) {
                continue;
            }

            int promoQuantity = productService.totalPurchaseItems(promotionProduct, promotionProduct.getQuantity());
            int totalQuantity = productService.totalPurchaseItems(promotionProduct, purchaseQuantity);

            if (promotionProduct.getQuantity() == purchaseQuantity) {
                continue;
            }
            //이때 수량 재세팅해야함
            if (promotionProduct.getQuantity() > purchaseQuantity) {
                if (purchaseQuantity - totalQuantity == promotionProduct.getPromotion().getBuy()) {
                    input = validateFreeItem(productName, promotionProduct.getPromotion().getGet());
                    if (input.equals("Y") || input.equals("y")) {
                        parseInput.put(productName, purchaseQuantity + promotionProduct.getPromotion().getGet());
                    }
                }
            }
            if (promotionProduct.getQuantity() < purchaseQuantity) {
                int soldOutItems = purchaseQuantity - promoQuantity;
                input = validateFreeItemSoldOut(productName, soldOutItems);
            }
        }
        return parseInput;
    }

    private String validateFreeItem(String productName, int freeItemQuantity) {
        do {
            try {
                input = InputView.promptCheckPromotion(productName, freeItemQuantity);
                pass = purchaseValidator.isEmptyInput(input);
                pass = yesNoValidator.isYesOrNo(input);
            } catch (ApiException e) {
                System.out.println(e.getMessage());
                pass = false;
            }
        } while (!pass);
        return input;
    }

    private String validateFreeItemSoldOut(String productName, int freeItemQuantity) {
        do {
            try {
                input = InputView.promptSoldOutPromotion(productName, freeItemQuantity);
                pass = purchaseValidator.isEmptyInput(input);
                pass = yesNoValidator.isYesOrNo(input);
            } catch (ApiException e) {
                System.out.println(e.getMessage());
                pass = false;
            }
        } while (!pass);
        return input;
    }
}
