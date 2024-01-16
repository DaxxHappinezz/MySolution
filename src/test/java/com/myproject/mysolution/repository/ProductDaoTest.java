package com.myproject.mysolution.repository;

import com.myproject.mysolution.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductDaoTest {

    @Autowired
    ProductDao productDao;

    @Test
    public void getCountTest() throws Exception {
        this.productDao.deleteAll();
        int rowCnt = this.productDao.count();
        assertTrue(rowCnt == 0);

        int price = 5000;

        for (int i = 1; i <= 5; i++) {
            Product product = new Product("count test product" + i, BigInteger.valueOf(100000), price*i);
            this.productDao.insert(product);
        }
        rowCnt = this.productDao.count();
        assertTrue(rowCnt == 5);
    }

    @Test
    public void selectTest() throws Exception {
        this.productDao.deleteAll();
        int rowCnt = this.productDao.count();
        assertTrue(rowCnt == 0);

        Product product = new Product("select test product", BigInteger.valueOf(100000), 5000);
        rowCnt = this.productDao.insert(product);
        assertTrue(rowCnt == 1);

        Product getProduct = this.productDao.selectByName(product.getProduct_name());
        assertTrue(Objects.equals(getProduct.getStorage_volume(), product.getStorage_volume()));

        Product getProduct2 = this.productDao.selectByNo(getProduct.getProduct_no());
        assertTrue(Objects.equals(getProduct2.getStorage_volume(), product.getStorage_volume()));
    }

    @Test
    public void insetTest() throws Exception {
        this.productDao.deleteAll();
        int rowCnt = this.productDao.count();
        assertTrue(rowCnt == 0);

        int count = 0;
        for (int i = 0; i < 10; i++) {
            Product product = new Product("insert test product"+i, BigInteger.valueOf(100000), 1000000);
            rowCnt = this.productDao.insert(product);
            count++;
        }
        System.out.println("count = " + count);
        rowCnt = this.productDao.count();
        assertTrue(rowCnt == 10);

    }

    @Test
    public void updateTest() throws Exception {
        this.productDao.deleteAll();
        int rowCnt = this.productDao.count();
        assertTrue(rowCnt == 0);

        Product product = new Product("update test product", BigInteger.valueOf(100000), 7000);
        rowCnt = this.productDao.insert(product);
        assertTrue(rowCnt == 1);

        Product getProduct = this.productDao.selectByName(product.getProduct_name());
        getProduct.setPrice(6000);
        rowCnt = this.productDao.update(getProduct);
        assertTrue(rowCnt == 1);

        assertTrue(Objects.equals(getProduct.getProduct_name(), product.getProduct_name()));


    }

    @Test
    public void deleteTest() throws Exception {
        this.productDao.deleteAll();
        int rowCnt = this.productDao.count();
        assertTrue(rowCnt == 0);

        for (int i = 0; i < 10; i++) {
            Product product = new Product("delete test product"+i, BigInteger.valueOf(100000), 1000000);
            rowCnt = this.productDao.insert(product);
        }
        rowCnt = this.productDao.count();
        assertTrue(rowCnt == 10);

        int randomNum = (int) (Math.random()*10) + 1;
        System.out.println("randomNum = " + randomNum);
        List<Product> products = this.productDao.selectAll();
        assertTrue(products.size() == 10);

        Product getProduct = this.productDao.selectByNo(products.get(randomNum).getProduct_no());
        System.out.println("getProduct = " + getProduct);
        rowCnt = this.productDao.delete(getProduct.getProduct_no());
        assertTrue(rowCnt == 1);

        rowCnt = this.productDao.count();
        assertTrue(rowCnt == 9);

    }

}