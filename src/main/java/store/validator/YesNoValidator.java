package store.validator;

import store.exception.ApiException;
import store.exception.ErrorCode;

public class YesNoValidator {

    public boolean isYesOrNo(String input) {
        input = input.replaceAll(" ", "");
        if (input.equals("Y") || input.equals("y")) {
            return true;
        }

        if (input.equals("N") || input.equals("n")) {
            return true;
        }
        throw new ApiException(ErrorCode.INVALID_FORM);
    }
}
