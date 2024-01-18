package com.myproject.mysolution.domain;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Subscription {
    private Integer subscription_no;
    private String user_id;
    private Integer product_no;
    private String product_name;
    private String company;
    private Integer member;
    private Integer month;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private LocalDateTime created;
    private LocalDateTime updated;

//    public Subscription(Integer user_no, Integer product_no, String company, Integer member, Integer month) {
//        this.user_no = user_no;
//        this.product_no = product_no;
//        this.company = company;
//        this.member = member;
//        this.month = month;
//    }

    public Subscription(String user_id, Integer product_no, String company, Integer member, Integer month) {
        this.user_id = user_id;
        this.product_no = product_no;
        this.company = company;
        this.member = member;
        this.month = month;
    }
    public Subscription(String user_id, String product_name, String company, Integer member, Integer month) {
        this.user_id = user_id;
        this.product_name = product_name;
        this.company = company;
        this.member = member;
        this.month = month;
    }
}
