package store.validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.constants.Delimiter;
import store.exception.ApiException;
import store.exception.ErrorCode;
import store.repository.ProductRepository;

public class PurchaseValidator {

    public final static Pattern ONE_PURCHASE_PATTERN = Pattern.compile("^\\[([가-힣A-Za-z0-9]+)-([0-9]+)\\]$");
    public final static Pattern ALL_PURCHASE_PATTERN = Pattern.compile(
            "^\\[([가-힣A-Za-z0-9]+)-([0-9]+)\\](,\\[([가-힣A-Za-z0-9]+)-([0-9]+)\\])*$");

    private final ProductRepository productRepository;

    public PurchaseValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public boolean validatePurchasePattern(String input) {
        Matcher matcher = ALL_PURCHASE_PATTERN.matcher(removeSpace(input));

        if (matcher.matches()) {
            return true;
        }
        throw new ApiException(ErrorCode.NOT_MATCH_PURCHASE_FORM);
    }

    public boolean checkPurchaseProductNameAndAmount(String input) {
        List<String> items = Delimiter.COMMA.splitDelimiter(removeSpace(input));

        for (String item : items) {
            Matcher oneMatcher = PurchaseValidator.ONE_PURCHASE_PATTERN.matcher(item);

            if (oneMatcher.matches()) {
                String productName = oneMatcher.group(1);
                int quantity = extractNumber(oneMatcher.group(2));

                if (!checkProductName(productName)) {
                    throw new ApiException(ErrorCode.NOT_FOUND_PRODUCT_NAME);
                }

                if (checkProductQuantity(productName) < quantity) {
                    throw new ApiException(ErrorCode.LACK_OF_QUANTITY);
                }
            }
        }
        return true;
    }

    public boolean isEmptyInput(String input) {
        if (input == null || input.isEmpty()) {
            throw new ApiException(ErrorCode.EMPTY_INPUT);
        }
        return true;
    }

    private boolean checkProductName(String productName) {
        return productRepository.findNames().contains(productName);
    }

    private int checkProductQuantity(String productName) {
        return productRepository.findQuantityByName(productName);
    }

    private int extractNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ApiException(ErrorCode.NOT_NUMBER);
        }
    }

    private String removeSpace(String input) {
        return input.replaceAll(" ", "");
    }
}
