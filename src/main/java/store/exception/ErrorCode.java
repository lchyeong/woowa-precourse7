package store.exception;

public enum ErrorCode {
    FILE_IS_EMPTY("I001", "[ERROR] 파일이 비었습니다."),
    NOT_FOUND_RESOURCE("I002", "[ERROR] 리소스를 찾을 수 없습니다."),
    INVALID_QUANTITY("I003", "[ERROR] 수량을 확인해주세요"),
    NOT_MATCH_PURCHASE_FORM("I004", "[ERROR] 입력을 확인해주세요."),
    NOT_NUMBER("I004", "[ERROR] 숫자를 입력해주세요."),
    INVALID_FORM("I005", "[ERROR] 잘못된 입력입니다."),
    INVALID_DATE_FORMAT("I006", "[ERROR] 날짜 형식이 맞지 않습니다."),
    EMPTY_INPUT("I007", "[ERROR] 입력이 비었습니다. 다시 확인해주세요."),
    REJECT_PROMOTION("I008", "[ERROR] 다시 처음으로( 이건 미출력)"),
    DUPLICATE_PRODUCT_NAME("I009", "[ERROR] 이름이 중복입니다. 다시 입력해주세요."),

    NOT_FOUND_PRODUCT_NAME("P001", "[ERROR] 제품 이름을 찾을 수 없습니다."),
    LACK_OF_QUANTITY("P002", "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    NO_PROMOTION("P003", "[ERROR] 현재 진행중인 프로모셥이 없습니다."),
    NOT_FOUND_PROMOTION_NAME("P004", "[ERROR] 프로모션 이름을 찾을 수 없습니다."),
    ;

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
