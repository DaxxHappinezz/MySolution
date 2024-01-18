package com.myproject.mysolution.repository;

import com.myproject.mysolution.domain.Product;
import com.myproject.mysolution.domain.Subscription;
import com.myproject.mysolution.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubscriptionDaoTest {

    @Autowired
    private SubscriptionDao subscriptionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    @Test
    public void getCountTest() throws Exception {
        this.subscriptionDao.deleteAll();
        int rowCnt = this.subscriptionDao.count();
        assertTrue(rowCnt == 0);

        this.userDao.deleteAll();
        rowCnt = this.userDao.count();
        assertTrue(rowCnt == 0);

        this.productDao.deleteAll();
        rowCnt = this.productDao.count();
        assertTrue(rowCnt == 0);

        User user = new User("test", "asdf", "testman");
        rowCnt = this.userDao.insert(user);
        assertTrue(rowCnt == 1);

        Product product = new Product("ace", BigInteger.valueOf(5000), 10000);
        rowCnt = this.productDao.insert(product);
        assertTrue(rowCnt == 1);

        User getUser = this.userDao.selectById("test");
        Product getProduct = this.productDao.selectByName("ace");

        Subscription subscription = new Subscription(getUser.getId(), getProduct.getProduct_no(), "test.com", 3, 12);
        rowCnt = this.subscriptionDao.insert(subscription);
        assertTrue(rowCnt == 1);
    }

    @Test
    public void insertTest() throws Exception {
        // subscription DB 초기화
        this.subscriptionDao.deleteAll();
        int rowCnt = this.subscriptionDao.count();
        assertTrue(rowCnt == 0);

        // users DB 초기화
        this.userDao.deleteAll();
        rowCnt = this.userDao.count();
        assertTrue(rowCnt == 0);

        // products DB 초기화
        this.productDao.deleteAll();
        rowCnt = this.productDao.count();
        assertTrue(rowCnt == 0);

        // users에 test data 추가
        User user = new User("test", "asdf", "testman");
        rowCnt = this.userDao.insert(user);
        assertTrue(rowCnt == 1);

        // products test data 추가
        Product product = new Product("ace", BigInteger.valueOf(5000), 10000);
        rowCnt = this.productDao.insert(product);
        assertTrue(rowCnt == 1);

        User getUser = this.userDao.selectById("test");
        Product getProduct = this.productDao.selectByName("ace");

        // subscription DB 접속 후 위 객체를 토대로 test date 추가
        Subscription subscription = new Subscription(getUser.getId(), getProduct.getProduct_no(), "test.com", 3, 12);
        rowCnt = this.subscriptionDao.insert(subscription);
        assertTrue(rowCnt == 1);

        // getUser에 user_no를 통해 구독 중인 데이터 가져오기
        Subscription subscription2 = this.subscriptionDao.selectByUserId(getUser.getId());
        assertTrue(Objects.equals(subscription.getSubscription_no(), subscription2.getSubscription_no()));
    }

    @Test
    public void updateMonthTest() throws Exception {
        // subscription DB 초기화
        this.subscriptionDao.deleteAll();
        int rowCnt = this.subscriptionDao.count();
        assertTrue(rowCnt == 0);

        // users DB 초기화
        this.userDao.deleteAll();
        rowCnt = this.userDao.count();
        assertTrue(rowCnt == 0);

        // products DB 초기화
        this.productDao.deleteAll();
        rowCnt = this.productDao.count();
        assertTrue(rowCnt == 0);

        // users, products DB에 테스트 data 추가(반복문 3번 씩)
        for (int i = 0; i < 4; i++) {
            User user = new User("test"+(i+1), "asdf", "testman");
            rowCnt = this.userDao.insert(user);
            assertTrue(rowCnt == 1);
            Product product = new Product("ace"+(i+1), BigInteger.valueOf(5000), 10000);
            rowCnt = this.productDao.insert(product);
            assertTrue(rowCnt == 1);
        }

        List<User> users = this.userDao.selectAll();
        assertTrue(users.size() == 4);

        List<Product> products = this.productDao.selectAll();
        assertTrue(products.size() == 4);

        // 각 12 개월로 구독 진행
        for (int i = 0; i < users.size(); i++) {
            Subscription subscription = new Subscription(users.get(i).getId(), products.get(i).getProduct_no(), "test.com"+(i+1), 10, 12);
            rowCnt = this.subscriptionDao.insert(subscription);
            assertTrue(rowCnt == 1);
        }

        List<Subscription> subscriptionList_before = this.subscriptionDao.selectAll();

        // 각 4, 5, 6, 7 개월 연장
        int month = 4;
        String userId = users.get(0).getId();
        Subscription tempSubs = this.subscriptionDao.selectByUserId(userId);
        int subsNo = tempSubs.getSubscription_no();

        for (int i = 0; i < users.size() ; i++) {
            Subscription changeSubsInfo = new Subscription(users.get(i).getId(), "", "test.com"+(i+1), 10, month);
            changeSubsInfo.setSubscription_no(subsNo);
            rowCnt = this.subscriptionDao.update(changeSubsInfo);
            assertTrue(rowCnt == 1);
            month++;
        }

        // 기존 구독 이력과 수정 후 이력 비교
        List<Subscription> subscriptionList_after = this.subscriptionDao.selectAll();
        for (int i = 0; i < subscriptionList_after.size(); i++) {
//            System.out.println("subscriptionList_before.get("+i+") = " + subscriptionList_before.get(i));
//            System.out.println("subscriptionList_after.get("+i+") = " + subscriptionList_after.get(i));
            System.out.println("before - user"+(i+1)+" no : " + subscriptionList_before.get(i).getUser_id() + " | month : " + subscriptionList_before.get(i).getMonth() + " - before");
            System.out.println("after  - user"+(i+1)+" no : " + subscriptionList_after.get(i).getUser_id() + " | month : " + subscriptionList_after.get(i).getMonth() + " - after");
        }

    }

}