/*
 * Copyright 2003-2016 Monitise Group Limited. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Monitise Group Limited.
 * Any reproduction of this material must contain this notice.
 */
package com.podcastmodern.cdi;

import com.podcastmodern.entity.Subscription;
import com.podcastmodern.service.SubscriptionService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserCDI {

    @Inject
    SubscriptionService subscriptionService;

    public String saveSubscription() {
        subscriptionService.saveSubscription(new Subscription(), 1,1 );
        return "";
    }

}
