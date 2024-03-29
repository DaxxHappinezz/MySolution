package com.myproject.mysolution.service;

import com.myproject.mysolution.domain.Subscription;
import com.myproject.mysolution.repository.SubscriptionDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubscriptionService {

    private final SubscriptionDao subscriptionDao;

    public SubscriptionService(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    public int getCount() throws Exception {
        return this.subscriptionDao.count();
    }
    public Subscription getSubscription(String user_id) throws Exception {
        return this.subscriptionDao.selectByUserId(user_id);
    }
    public List<Subscription> checkSubscription(String user_id, String company) throws Exception {
        Map check = new HashMap<>();
        check.put("user_id", user_id);
        check.put("company", company);
        return this.subscriptionDao.selectByDuplication(check);
    }
    public List<Subscription> getSubscriptionList() throws Exception {
        return this.subscriptionDao.selectAll();
    }
    public int doSubscription(Subscription subscription) throws Exception {
        return this.subscriptionDao.insert(subscription);
    }
    public int modify(Subscription subscription) throws Exception {
        return this.subscriptionDao.update(subscription);
    }
    public int remove(String user_id) throws Exception {
        return this.subscriptionDao.delete(user_id);
    }
    public int removeAll() throws Exception {
        return this.subscriptionDao.deleteAll();
    }

}
