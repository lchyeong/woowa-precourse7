package store.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import org.junit.jupiter.api.Test;

class PurchaseValidatorTest {

    @Test
    void 구매_입력_테스트() {
        String input1 = "[콜라-1]";
        String input2 = "[사이다 - 1 ]";
        String input3 = "[콜라-1], [사이다-3]";
        Matcher matcher1 = PurchaseValidator.ALL_PURCHASE_PATTERN.matcher(removeSpace(input1));
        Matcher matcher2 = PurchaseValidator.ALL_PURCHASE_PATTERN.matcher(removeSpace(input2));
        Matcher matcher3 = PurchaseValidator.ALL_PURCHASE_PATTERN.matcher(removeSpace(input3));

        assertTrue(matcher1.matches());
        assertTrue(matcher2.matches());
        assertTrue(matcher3.matches());
    }

    @Test
    void 제품_있는지_테스트() {
        String input1 = "[콜라-1]";
        String input2 = "[사이다-2]";

        Matcher matcher1 = PurchaseValidator.ONE_PURCHASE_PATTERN.matcher(removeSpace(input1));
        Matcher matcher2 = PurchaseValidator.ONE_PURCHASE_PATTERN.matcher(removeSpace(input2));

        if (matcher1.matches() && matcher2.matches()) {
            assertEquals("콜라", matcher1.group(1));
            assertEquals("1", matcher1.group(2));
            assertEquals("사이다", matcher2.group(1));
            assertEquals("2", matcher2.group(2));
        }
    }

    private String removeSpace(String input) {
        return input.replaceAll(" ", "");
    }
}