package com.myproject.mysolution.repository;

import com.myproject.mysolution.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductDao {

    @Select("SELECT count(*) FROM products")
    int count() throws Exception;
    @Select("SELECT * FROM products WHERE product_no = #{product_no}")
    Product selectByNo(Integer product_no) throws Exception;
    @Select("SELECT * FROM products WHERE product_name = #{product_name}")
    Product selectByName(String product_name) throws Exception;
    @Select("SELECT * FROM products")
    List<Product> selectAll() throws Exception;
    @Insert("INSERT INTO products (product_name, storage_volume, price) " +
            "VALUES (#{product.product_name}, #{product.storage_volume}, #{product.price})")
    int insert(@Param("product") Product product) throws Exception;
    @Update("UPDATE products SET product_name = #{product.product_name}, storage_volume = #{product.storage_volume}, price = #{product.price} " +
            "WHERE product_no = #{product.product_no}")
    int update(@Param("product") Product product) throws Exception;
    @Delete("DELETE FROM products WHERE product_no = #{product_no}")
    int delete(Integer product_no) throws Exception;
    @Delete("DELETE FROM products")
    int deleteAll() throws Exception;
}
