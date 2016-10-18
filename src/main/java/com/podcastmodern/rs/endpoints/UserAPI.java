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
import com.podcastmodern.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.PathParam;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserAPI {

    GenericDao genericDao;

    @RequestMapping(value = "insert", method = RequestMethod.POST)
    @ResponseBody
    public Integer getApplication(@RequestParam String userString) throws IOException, NamingException {

        User user =
            new ObjectMapper().readValue(userString, User.class);

        genericDao = (GenericDao) new InitialContext().lookup("java:global/PodcastModern/GenericDao");

        genericDao.save(user);

        return user.getUserId();

    }

    @RequestMapping(value = "/{userId}/subscribe", method = RequestMethod.POST)
    @ResponseBody
    public void getApplication(@RequestParam String userString, @PathParam("userId") String userId, @RequestParam String sku)
        throws IOException, NamingException {

        User user =
            new ObjectMapper().readValue(userString, User.class);

        genericDao = (GenericDao) new InitialContext().lookup("java:global/PodcastModern/GenericDao");

        genericDao.save(user);

    }


}
