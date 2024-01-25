package com.myproject.mysolution.service;

import com.myproject.mysolution.domain.User;
import com.myproject.mysolution.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public int getCount() throws Exception {
        return this.userDao.count();
    }
    public User getUserById(String id) throws Exception {
        return this.userDao.selectById(id);
    }
    public User getUserByName(String id, String name) throws Exception {
        Map userInfo = new HashMap<>();
        userInfo.put("id", id);
        userInfo.put("name", name);
        return this.userDao.selectByName(userInfo);
    }
    public List<User> userList() throws Exception {
        return this.userDao.selectAll();
    }
    public int registration(User user) throws Exception {
        User isExistUser = this.userDao.selectById(user.getId());
        if (isExistUser != null) {
            throw new Exception("Requested User Id already used.");
        }
        return this.userDao.insert(user);
    }
    public int modifyInfo(User user) throws Exception {
        return this.userDao.update(user);
    }
    public int remove(String id) throws Exception {
        return this.userDao.delete(id);
    }
    public int removeAll() throws Exception {
        return this.userDao.deleteAll();
    }
}
