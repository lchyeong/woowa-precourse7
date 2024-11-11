package store.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import store.exception.ApiException;
import store.exception.ErrorCode;

public class DateUtil {

    public static LocalDateTime toDate(String input) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            input = input.trim();
            LocalDate date = LocalDate.parse(input, format);

            return date.atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new ApiException(ErrorCode.INVALID_DATE_FORMAT);
        }
    }
}
