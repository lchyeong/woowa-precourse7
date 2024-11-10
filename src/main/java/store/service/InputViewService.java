package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import store.entity.Product;
import store.exception.ApiException;
import store.repository.ProductRepository;
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

    public String checkPromotion(Map<String, Integer> parseInput) {
        LocalDateTime now = DateTimes.now();
        LocalDate currentDate = now.toLocalDate();
        for (Map.Entry<String, Integer> entry : parseInput.entrySet()) {
            String productName = entry.getKey();
            int purchaseQuantity = entry.getValue();

            Product promotionProduct = productRepository.findPromotionProductByName(productName);
            int freeItemQuantity = productService.getAdditionalFreeItems(promotionProduct, purchaseQuantity);

            if (promotionProduct == null) {
                continue;
            }
            LocalDate startDate = promotionProduct.getPromotion().getStartDate().toLocalDate();
            LocalDate endDate = promotionProduct.getPromotion().getEndDate().toLocalDate();

            if (currentDate.isBefore(startDate) || currentDate.isAfter(endDate)) {
                continue;
            }

            if (promotionProduct.getPromotion() != null && freeItemQuantity == promotionProduct.getPromotion()
                    .getGet()) {
                input = validateFreeItem(promotionProduct, freeItemQuantity);
            }
            if (promotionProduct.getQuantity() < purchaseQuantity) {
                input = validateFreeItemSoldOut(promotionProduct, freeItemQuantity);
            }
        }
        return input;
    }

    private String validateFreeItem(Product promotionProduct, int freeItemQuantity) {
        do {
            try {
                input = InputView.promptCheckPromotion(promotionProduct, freeItemQuantity);
                pass = purchaseValidator.isEmptyInput(input);
                pass = yesNoValidator.isYesOrNo(input);
            } catch (ApiException e) {
                System.out.println(e.getMessage());
                pass = false;
            }
        } while (!pass);
        return input;
    }

    private String validateFreeItemSoldOut(Product promotionProduct, int freeItemQuantity) {
        do {
            try {
                input = InputView.promptSoldOutPromotion(promotionProduct, freeItemQuantity);
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
