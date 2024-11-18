package store.validator;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import store.entity.Product;

public class DateValidator {


    public static boolean checkPromotionDate(Product promotionProduct) {
        LocalDateTime now = DateTimes.now();
        LocalDate currentDate = now.toLocalDate();

        LocalDate startDate = promotionProduct.getPromotion().getStartDate().toLocalDate();
        LocalDate endDate = promotionProduct.getPromotion().getEndDate().toLocalDate();
        return !currentDate.isBefore(startDate) && !currentDate.isAfter(endDate);
    }
}
