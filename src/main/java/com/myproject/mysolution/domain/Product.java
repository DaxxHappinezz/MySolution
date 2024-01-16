package com.myproject.mysolution.domain;

import jakarta.websocket.server.ServerEndpoint;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    private Integer product_no;
    private String product_name;
    @NumberFormat(pattern = "###,###")
    private BigInteger storage_volume;
    @NumberFormat(pattern = "###,###")
    private Integer price;

    public Product(String product_name, BigInteger storage_volume, Integer price) {
        this.product_name = product_name;
        this.storage_volume = storage_volume;
        this.price = price;
    }
}
