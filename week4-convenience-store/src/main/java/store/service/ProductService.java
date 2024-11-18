package store.service;

import store.entity.Product;
import store.repository.ProductRepository;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void getRemainQuantityAfterPromotion(String productName, int purchaseQuantity) {
        int remainQuantity = handlePromotionStock(productName, purchaseQuantity);
        handleRegularStock(productName, remainQuantity);
    }

    public int handlePromotionStock(String productName, int purchaseQuantity) {
        int remainQuantity = purchaseQuantity;

        Product promotionProduct = productRepository.findPromotionProductByName(productName);
        if (promotionProduct != null && promotionProduct.getQuantity() > 0) {
            int availablePromotionStock = Math.min(promotionProduct.getQuantity(), purchaseQuantity);
            remainQuantity -= availablePromotionStock;

            promotionProduct.decreaseQuantity(availablePromotionStock);
        }

        return remainQuantity;
    }

    public void handleRegularStock(String productName, int remainQuantity) {
        if (remainQuantity > 0) {
            Product regularProduct = productRepository.findProductByName(productName);
            regularProduct.decreaseQuantity(remainQuantity);
        }
    }

    public int getFreeItems(Product promotionProduct, int purchaseQuantity) {
        int buy = promotionProduct.getPromotion().getBuy();
        int get = promotionProduct.getPromotion().getGet();

        return purchaseQuantity / (buy + get);
    }

    public int getRealPurchaseItems(Product promotionProduct, int purchaseQuantity) {
        return promotionProduct.getPromotion().getBuy() * getFreeItems(promotionProduct, purchaseQuantity);
    }

    public int totalPurchaseItems(Product promotionProduct, int purchaseQuantity) {
        return getFreeItems(promotionProduct, purchaseQuantity) + getRealPurchaseItems(promotionProduct,
                purchaseQuantity);
    }

    public int getFreeItemPercent(Product promotionProduct, int purchaseQuantity) {
        int buy = promotionProduct.getPromotion().getBuy();
        int get = promotionProduct.getPromotion().getGet();
        return purchaseQuantity % (buy + get);
    }

    public int getBuyPlusGet(Product promotionProduct) {
        int buy = promotionProduct.getPromotion().getBuy();
        int get = promotionProduct.getPromotion().getGet();
        return buy + get;
    }
}
