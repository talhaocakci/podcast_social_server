package com.podcastmodern.rs.endpoints;

import com.podcastmodern.dao.ApplicationDao;
import com.podcastmodern.dao.GenericDao;
import com.podcastmodern.entity.Application;
import com.podcastmodern.entity.ApplicationUser;
import com.podcastmodern.entity.ApplicationUserId;
import com.podcastmodern.rs.endpoints.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/application/{appid}")
public class PodcastModernAPI {


    ApplicationDao applicationDao;
    GenericDao genericDao;

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    Application getApplication(@PathVariable Integer appid) {

        try {
            applicationDao = (ApplicationDao) new InitialContext().lookup("java:global/PodcastModern/ApplicationDao");
            System.out.println(applicationDao);
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Application app = applicationDao.findApplicationById(appid);
        return app;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces="application/json")
    @ResponseBody
    public Integer registerUser(@RequestBody User user, @PathVariable Integer appid) {
        try {

            com.podcastmodern.entity.User userEntity = new com.podcastmodern.entity.User();
            userEntity.setCountry(user.getLocation());
            userEntity.setDisplayName(user.getDisplayName());
            userEntity.setEmail(user.getEmail());
            userEntity.setName(user.getName());
            userEntity.setSurname(user.getSurname());
            userEntity.setGcmToken(user.getGcmToken());
            userEntity.setFbToken(user.getFbToken());
            userEntity.setGoogleToken(user.getGoogleToken());

            genericDao = (GenericDao) new InitialContext().lookup("java:global/PodcastModern/GenericDao");

            Set<Application> applicaationSet = new HashSet<Application>();
            Set<com.podcastmodern.entity.User> userSet = new HashSet<com.podcastmodern.entity.User>();
            userSet.add(userEntity);

            Application app = (Application) genericDao.get(Application.class, appid);
            ApplicationUser appUser = new ApplicationUser();
            appUser.setApplication(app);

            appUser.setUser(userEntity);
            Set<ApplicationUser> appUsers = new HashSet<ApplicationUser>();
            appUsers.add(appUser);
            app.setApplicationUsers(appUsers);

            applicaationSet.add(app);

            genericDao.save(user);

            ApplicationUserId id = new ApplicationUserId(userEntity.getUserId(), appid);
            appUser.setId(id);

            appUser.setFirstRegisterTime(new Date());
            for (ApplicationUser appUserInternal : app.getApplicationUsers())
                genericDao.save(appUserInternal);

            return userEntity.getUserId();

        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

}