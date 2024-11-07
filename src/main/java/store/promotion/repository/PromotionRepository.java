package store.promotion.repository;

import java.util.LinkedList;
import java.util.List;
import store.exception.ApiException;
import store.exception.ErrorCode;
import store.promotion.entity.Promotion;

public class PromotionRepository {
    private static PromotionRepository instance;
    private List<Promotion> promotions;

    private PromotionRepository() {
        this.promotions = new LinkedList<>();
    }

    public static PromotionRepository getInstance() {
        if (instance == null) {
            throw new ApiException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        return instance;
    }

    public void init(List<Promotion> promotions) {
        this.promotions = promotions;
    }
}
