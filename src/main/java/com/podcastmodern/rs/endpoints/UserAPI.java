/*
 * Copyright 2003-2016 Monitise Group Limited. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Monitise Group Limited.
 * Any reproduction of this material must contain this notice.
 */
package com.podcastmodern.rs.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.podcastmodern.dao.GenericDao;
import com.podcastmodern.entity.Subscription;
import com.podcastmodern.entity.User;
import com.podcastmodern.rs.endpoints.entity.LoginRequest;
import com.podcastmodern.service.SubscriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Controller
@RequestMapping("/user")
public class UserAPI {

    GenericDao genericDao;

    SubscriptionService subscriptionService;

    @RequestMapping(value = "insert", method = RequestMethod.POST)
    @ResponseBody
    public Integer getApplication(@RequestParam String userString) throws IOException, NamingException {

        User user =
            new ObjectMapper().readValue(userString, User.class);

        genericDao = (GenericDao) new InitialContext().lookup("java:global/PodcastModern/GenericDao");

        genericDao.save(user);

        return user.getUserId();

    }

    @RequestMapping(value = "subscribe/{userId}/{appId}", method = RequestMethod.POST, consumes = "application/json",
        produces =
        "application/json")
    @ResponseBody
    public Long getApplication(@RequestBody Subscription subscription,  @PathVariable("userId") Integer userId,
                               @PathVariable("appId") Integer appId) throws NamingException {

        subscriptionService = (SubscriptionService) new InitialContext().lookup("java:global/PodcastModern/SubscriptionService");

        Subscription subs =  subscriptionService.saveSubscription(subscription, appId, userId);

        return subs.getSubscriptionId();

    }

    @RequestMapping(value = "loginByGoogle", method = RequestMethod.POST, consumes = "application/json", produces =
        "application/json")
    @ResponseBody
    public void loginByGoogle(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest) throws
        GeneralSecurityException, IOException {
        User verifiedUser = PodcastModernAPI.verifyGoogleToken(loginRequest.getGoogleToken());

        if(verifiedUser != null) {
            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute("membermode", "premium");
        }

    }

}
