package store.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import store.entity.Product;

public class ProductRepository {
    private static ProductRepository instance;
    private List<Product> products;

    public ProductRepository() {
        this.products = new LinkedList<>();
    }

    public static ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }
        return instance;
    }

    public void init(List<Product> products) {
        this.products = products;
    }

    public List<Product> findAll() {
        return products;
    }

    public List<Product> findProductsByNames(String productName) {
        List<Product> productsWithName = new ArrayList<>();
        for (Product product : products) {
            if (productName.equals(product.getName())) {
                productsWithName.add(product);
            }
        }
        return productsWithName;
    }

    public Set<String> findNames() {
        Set<String> names = new HashSet<>();
        for (Product product : products) {
            names.add(product.getName());
        }
        return names;
    }

    public int findQuantityByName(String name) {
        int totalQuantity = 0;
        for (Product product : products) {
            if (name.equals(product.getName())) {
                totalQuantity += product.getQuantity();
            }
        }
        return totalQuantity;
    }

    public int findPromotionQuantityByName(String name) {
        int promotionQuantity = 0;
        for (Product product : products) {
            if (name.equals(product.getName()) && product.getPromotion() != null) {
                promotionQuantity = product.getQuantity();
            }
        }
        return promotionQuantity;
    }

    public Product findPromotionProductByName(String name) {
        for (Product product : products) {
            if (name.equals(product.getName()) && product.getPromotion() != null) {
                return product;
            }
        }
        return null;
    }

    public Product findProductByName(String name) {
        for (Product product : products) {
            if (name.equals(product.getName()) && product.getPromotion() == null) {
                return product;
            }
        }
        return null;
    }
}
