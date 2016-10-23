/*
 * Copyright 2003-2016 Monitise Group Limited. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Monitise Group Limited.
 * Any reproduction of this material must contain this notice.
 */
package com.podcastmodern.service;

import com.podcastmodern.dao.ApplicationDao;
import com.podcastmodern.dao.GenericDao;
import com.podcastmodern.entity.Subscription;
import com.podcastmodern.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;

@Stateless
public class SubscriptionService implements Serializable {

    @Inject
    private GenericDao genericDao;

    @Inject
    private ApplicationDao applicationDao;

    public void saveSubscription(Subscription subscription, Integer appId, Integer userId) {

        subscription.setApplication(applicationDao.findApplicationById(appId));

        subscription.setUser((User) genericDao.load(User.class, userId));

        genericDao.save(subscription);
    }


}
