package store.entity;

import java.util.Map;
import java.util.Objects;
import store.exception.ApiException;
import store.exception.ErrorCode;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private Promotion promotion;

    public Product(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public Product(Map<String, String> data, Promotion promotion) {
        this.name = data.getOrDefault("name", "");
        this.price = extractNumber(data.getOrDefault("price", "0"));
        this.quantity = extractNumber(data.getOrDefault("quantity", "0"));
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public String getPromotionName() {
        if (promotion == null) {
            return "";
        }
        return promotion.getName();
    }

    public void decreaseQuantity(int amount) {
        if (amount > quantity) {
            throw new ApiException(ErrorCode.LACK_OF_QUANTITY);
        }
        this.quantity -= amount;
    }


    private int extractNumber(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new ApiException(ErrorCode.NOT_NUMBER);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return name.equals(product.name) &&
                price == product.price &&
                quantity == product.quantity &&
                Objects.equals(promotion, product.promotion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, quantity, promotion);
    }
}
