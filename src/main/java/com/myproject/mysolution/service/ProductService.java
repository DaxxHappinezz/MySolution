package com.myproject.mysolution.service;

import com.myproject.mysolution.domain.Product;
import com.myproject.mysolution.repository.ProductDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public int getCount() throws Exception {
        return this.productDao.count();
    }
    public Product getProductByNo(Integer product_no) throws Exception {
        return this.productDao.selectByNo(product_no);
    }
    public Product getProductByName(String product_name) throws Exception {
        return this.productDao.selectByName(product_name);
    }
    public List<Product> getProductList() throws Exception {
        return this.productDao.selectAll();
    }
    public int registration(Product product) throws Exception {
        return this.productDao.insert(product);
    }
    public int modify(Product product) throws Exception {
        return this.productDao.update(product);
    }
    public int remove(Integer product_no) throws Exception {
        return this.productDao.delete(product_no);
    }
}
