package com.myproject.mysolution.repository;

import com.myproject.mysolution.domain.Subscription;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SubscriptionDao {

    @Select("SELECT count(*) FROM subscription")
    int count() throws Exception;
//    @Select("SELECT * FROM subscription WHERE user_id = #{user_id}")
    @Select("SELECT s.subscription_no, s.user_id, p.product_name, s.company, s.member, s.month, s.start_date, s.end_date\n" +
            "FROM subscription s LEFT JOIN products p ON s.product_no = p.product_no " +
            "WHERE user_id = #{user_id}")
    Subscription selectByUserId(String id) throws Exception;
    @Select("SELECT * FROM subscription WHERE user_id = #{check.user_id} AND company = #{check.company}")
    List<Subscription> selectByDuplication(@Param("check") Map check) throws Exception;
    @Select("SELECT * FROM subscription")
    List<Subscription> selectAll() throws Exception;
    @Insert("INSERT INTO subscription (user_id, product_no, company, member, month, start_date, end_date, created, updated) " +
            "VALUES (#{subscription.user_id}, #{subscription.product_no}, #{subscription.company}, #{subscription.member}, #{subscription.month}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)")
    int insert(@Param("subscription") Subscription subscription) throws Exception;
    @Update("UPDATE subscription SET company = #{subs_info.company}, member = #{subs_info.member}, month = month + #{subs_info.month}, end_date = DATE_ADD(end_date, interval #{subs_info.month} month), updated = CURRENT_TIMESTAMP " +
            "WHERE user_id = #{subs_info.user_id} AND product_no = #{subs_info.product_no}")
    int update(@Param("subs_info") Subscription subscription) throws Exception;
    @Delete("DELETE FROM subscription WHERE user_id = #{user_id}")
    int delete(String user_id) throws Exception;
    @Delete("DELETE FROM subscription")
    int deleteAll() throws Exception;
}
