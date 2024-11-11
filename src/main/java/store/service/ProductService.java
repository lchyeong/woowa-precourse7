package store.service;

import store.entity.Product;
import store.repository.ProductRepository;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public int calculateNoPromotionProduct(Product product, int purchaseQuantity) {
        return product.getQuantity() - purchaseQuantity;
    }

    private int getRemainQuantityAfterPromotion(Product promotionProduct, int purchaseQuantity) {
        int promotionCount = getPromotionCount(promotionProduct, purchaseQuantity);

        if (promotionCount == 0) {
            return purchaseQuantity;
        }

        int totalPromotionQuantity = getPromotionQuantity(promotionProduct, purchaseQuantity);
        int remainingQuantity = purchaseQuantity - totalPromotionQuantity;

        if (remainingQuantity < 0) {
            remainingQuantity = 0;
        }
        return remainingQuantity;
    }

    public int getPromotionQuantity(Product promotionProduct, int purchaseQuantity) {
        int buy = promotionProduct.getPromotion().getBuy();
        int get = promotionProduct.getPromotion().getGet();

        return (buy + get) * getPromotionCount(promotionProduct, purchaseQuantity);
    }

    public int getPromotionCount(Product promotionProduct, int purchaseQuantity) {
        int buy = promotionProduct.getPromotion().getBuy();
        int get = promotionProduct.getPromotion().getGet();

        if ((purchaseQuantity / (buy + get)) <= 0) {
            return 0;
        }
        return purchaseQuantity / (buy + get);
    }

    public int getAdditionalFreeItems(Product promotionProduct, int purchaseQuantity) {
        if (promotionProduct == null) {
            return 0;
        }
        int buy = promotionProduct.getPromotion().getBuy();
        int get = promotionProduct.getPromotion().getGet();

        if (purchaseQuantity % (buy + get) == 0 && (promotionProduct.getQuantity() >= purchaseQuantity)) {
            return (purchaseQuantity / (buy + get)) * get;
        }

        if (purchaseQuantity % (buy + get) == buy && (promotionProduct.getQuantity() >= purchaseQuantity)) {
            return get;
        }

        if (promotionProduct.getQuantity() < purchaseQuantity) {
            return purchaseQuantity - ((purchaseQuantity / (buy + get)) * (buy + get));
        }
        return 0;
    }
}
