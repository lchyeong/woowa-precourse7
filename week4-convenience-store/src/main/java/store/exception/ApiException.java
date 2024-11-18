package store.exception;

public class ApiException extends RuntimeException {


    private final String code;

    public ApiException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}
