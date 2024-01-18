package com.myproject.mysolution.repository;

import com.myproject.mysolution.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDaoTest {

    @Autowired UserDao userDao;

    @Test
    public void getCountTest() throws Exception {
        this.userDao.deleteAll();
        int rowCnt = this.userDao.count();
        assertTrue(rowCnt == 0);

        for (int i = 1; i <= 10; i++) {
            User user = new User("test"+i, "asdf", "testman");
            rowCnt = this.userDao.insert(user);
            assertTrue(rowCnt == 1);
        }

        rowCnt = this.userDao.count();
        assertTrue(rowCnt == 10);
    }

    @Test
    public void insertTest() throws Exception {
        this.userDao.deleteAll();
        int rowCnt = this.userDao.count();
        assertTrue(rowCnt == 0);

        User user = new User("test", "asdf", "testman");
        rowCnt = this.userDao.insert(user);
        assertTrue(rowCnt == 1);

    }

    @Test
    public void selectByIdTest() throws Exception {
        this.userDao.deleteAll();
        int rowCnt = this.userDao.count();
        assertTrue(rowCnt == 0);

        User user = new User("hello", "asdf", "testman");
        rowCnt = this.userDao.insert(user);
        assertTrue(rowCnt == 1);

        User user2 = this.userDao.selectById(user.getId());
        assertTrue(user2.getId().equals("hello"));

    }

    @Test
    public void selectByNameTest() throws Exception {
        this.userDao.deleteAll();
        int rowCnt = this.userDao.count();
        assertTrue(rowCnt == 0);

        User user = new User("hello", "asdf", "testman");
        rowCnt = this.userDao.insert(user);
        assertTrue(rowCnt == 1);
        user = this.userDao.selectById("hello");

        Map userInfo = new HashMap();
        userInfo.put("id", "hello");
        userInfo.put("name", "testman");
        User user2 = this.userDao.selectByName(userInfo);
        assertTrue(user2.getUser_no() == user.getUser_no());

    }

    @Test
    public void selectAllTest() throws Exception {
        this.userDao.deleteAll();
        int rowCnt = this.userDao.count();
        assertTrue(rowCnt == 0);

        for (int i = 1; i <= 10; i++) {
            User user = new User("test"+i, "asdf", "testman");
            rowCnt = this.userDao.insert(user);
            assertTrue(rowCnt == 1);
        }

        List<User> userList = this.userDao.selectAll();
        for (User user : userList) {
            System.out.println("user = " + user);
        }
        assertTrue(userList.size() == 10);

        rowCnt = this.userDao.count();
        assertTrue(rowCnt == 10);

    }

    @Test
    public void updateTest() throws Exception {
        this.userDao.deleteAll();
        int rowCnt = this.userDao.count();
        assertTrue(rowCnt == 0);

        User user = new User("hello", "asdf", "testman");
        rowCnt = this.userDao.insert(user);
        assertTrue(rowCnt == 1);
        user = this.userDao.selectById("hello");

        user.setPassword("qwer");
        System.out.println("modifyUser = " + user);
        rowCnt = this.userDao.update(user);
        assertTrue(rowCnt == 1);

        User user2 = this.userDao.selectById("hello");
        assertTrue(user2.getPassword().equals("qwer"));
        assertTrue(user2.getPassword() != user.getPassword());
    }

    @Test
    public void deleteTest() throws Exception {
        this.userDao.deleteAll();
        int rowCnt = this.userDao.count();
        assertTrue(rowCnt == 0);

        User user = new User("hello", "asdf", "testman");
        rowCnt = this.userDao.insert(user);
        assertTrue(rowCnt == 1);
        user = this.userDao.selectById("hello");

        rowCnt = this.userDao.delete(user.getId());
        assertTrue(rowCnt == 1);

        rowCnt = this.userDao.count();
        assertTrue(rowCnt == 0);

    }
}