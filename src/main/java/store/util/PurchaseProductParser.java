package store.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import store.constants.Delimiter;
import store.exception.ApiException;
import store.exception.ErrorCode;
import store.validator.PurchaseValidator;

public class PurchaseProductParser {

    public Map<String, Integer> parsePurchaseInput(String input) {
        Map<String, Integer> result = new HashMap<>();
        List<String> items = Delimiter.COMMA.splitDelimiter(removeSpace(input));

        for (String item : items) {
            Matcher oneMatcher = PurchaseValidator.ONE_PURCHASE_PATTERN.matcher(item);

            if (oneMatcher.matches()) {
                String productName = oneMatcher.group(1);
                int quantity = extractNumber(oneMatcher.group(2));

                result.put(productName, quantity);
            }
        }
        return result;
    }

    private String removeSpace(String input) {
        return input.replaceAll(" ", "");
    }

    private int extractNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ApiException(ErrorCode.NOT_NUMBER);
        }
    }
}
