package store.repository;

import java.util.LinkedList;
import java.util.List;
import store.entity.Promotion;

public class PromotionRepository {
    private static PromotionRepository instance;
    private List<Promotion> promotions;

    public PromotionRepository() {
        this.promotions = new LinkedList<>();
    }

    public static PromotionRepository getInstance() {
        if (instance == null) {
            instance = new PromotionRepository();
        }
        return instance;
    }

    public void init(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public Promotion findPromotionWithName(String name) {
        for (Promotion promotion : promotions) {
            if (name.equals(promotion.getName())) {
                return promotion;
            }
        }
        return null;
    }
}
