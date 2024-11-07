package store.product.repository;

import java.util.LinkedList;
import java.util.List;
import store.exception.ApiException;
import store.exception.ErrorCode;
import store.product.entity.Product;

public class ProductRepository {
    private static ProductRepository instance;
    private List<Product> products;

    public ProductRepository() {
        this.products = new LinkedList<>();
    }

    public ProductRepository getInstance() {
        if (instance == null) {
            throw new ApiException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        return instance;
    }

    public void init(List<Product> products) {
        this.products = products;
    }
}
