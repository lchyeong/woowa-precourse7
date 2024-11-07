package store.product.entity;

import java.util.Map;
import store.promotion.entity.Promotion;

public class Product {

    private final String name;
    private final String price;
    private final String quantity;
    private final Promotion promotion;

    public Product(String name, String price, String quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public Product(Map<String, String> data) {
        this.name = data.getOrDefault("name", "");
        this.price = data.getOrDefault("price", "");
        this.quantity = data.getOrDefault("quantity", "");
        this.promotion = null;
//
//        if(data.containsKey("promotion"){
//            this.promotion = validatePromotion(data.get("promotion"));
//        }
    }

//    public Promotion validatePromotion(String promotionName){
//
//
//        return new Promotion(promotionDto);
////    }
//    private Promotion vaPro(Promotion promotion, String promotionName){
//        if(promotion.getName().equals(promotionName)){
//
//        }
//    }
}
