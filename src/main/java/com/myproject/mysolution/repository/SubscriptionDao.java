package com.myproject.mysolution.repository;

import com.myproject.mysolution.domain.Subscription;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SubscriptionDao {

    @Select("SELECT count(*) FROM subscription")
    int count() throws Exception;
    @Select("SELECT * FROM subscription WHERE user_no = #{user_no}")
    Subscription selectByUserNo(Integer user_no) throws Exception;
    @Select("SELECT * FROM subscription WHERE user_no = #{check.user_no} AND company = #{check.company}")
    List<Subscription> selectByDuplication(@Param("check") Map check) throws Exception;
    @Select("SELECT * FROM subscription")
    List<Subscription> selectAll() throws Exception;
    @Insert("INSERT INTO subscription (user_no, product_no, company, member, month, start_date, end_date, created, updated) " +
            "VALUES (#{subscription.user_no}, #{subscription.product_no}, #{subscription.company}, #{subscription.member}, #{subscription.month}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)")
    int insert(@Param("subscription") Subscription subscription) throws Exception;
    @Update("UPDATE subscription SET company = #{subs_info.company}, member = #{subs_info.member}, month = month + #{subs_info.month}, end_date = DATE_ADD(end_date, interval #{subs_info.month} month), updated = CURRENT_TIMESTAMP " +
            "WHERE user_no = #{subs_info.user_no} AND #{subs_info.subscription_no}")
    int update(@Param("subs_info") Subscription subscription) throws Exception;
    @Delete("DELETE FROM subscription WHERE user_no = #{user_no}")
    int delete(Integer user_no) throws Exception;
    @Delete("DELETE FROM subscription")
    int deleteAll() throws Exception;
}
