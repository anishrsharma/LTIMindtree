package com.wecp.progressive.dao;

// import java.util.ArrayList;
import java.util.List;

import com.wecp.progressive.entity.Product;

public class ProductDAOImpl implements ProductDAO{

    // List<Product> productList = new ArrayList<>();

    @Override
    public int addProduct(Product product) {
        return -1;
    }

    @Override
    public Product getProductById(int productId) {
        return null;
    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void deleteProduct(int productId) {

    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

}
