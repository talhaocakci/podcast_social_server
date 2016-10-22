package com.podcastmodern.rs.endpoints;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
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
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/application/{appid}")
public class PodcastModernAPI {


    ApplicationDao applicationDao;
    GenericDao genericDao;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Application getApplication(@PathVariable Integer appid, HttpServletRequest request) {

        try {
            applicationDao = (ApplicationDao) new InitialContext().lookup("java:global/PodcastModern/ApplicationDao");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Application app = applicationDao.findApplicationById(appid);
        return app;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Integer registerUser(@RequestBody User user, @PathVariable Integer appid)
        throws GeneralSecurityException, IOException {

        try {
            com.podcastmodern.entity.User userEntity = new com.podcastmodern.entity.User();
            userEntity.setCountry(user.getLocation());
            userEntity.setDisplayName(user.getDisplayName());
            userEntity.setEmail(user.getEmail());
            userEntity.setName(user.getName());
            userEntity.setSurname(user.getSurname());
            userEntity.setGcmToken(user.getGcmToken());
            userEntity.setFbToken(user.getFbToken());

            // push google googleToken id to server and put the result into googleToken attribute of user
            com.podcastmodern.entity.User verifiedUser =
                verifyGoogleToken(user.getGooogleIdTokenForServer());

            userEntity.setGoogleToken(user.getGoogleToken());

            genericDao = (GenericDao) new InitialContext().lookup("java:global/PodcastModern/GenericDao");

            Set<Application> applicationSet = new HashSet<Application>();
            Set<com.podcastmodern.entity.User> userSet = new HashSet<com.podcastmodern.entity.User>();
            userSet.add(userEntity);

            Application app = (Application) genericDao.get(Application.class, appid);
            ApplicationUser appUser = new ApplicationUser();
            if (app != null) {

                appUser.setApplication(app);
                appUser.setUser(userEntity);

                Set<ApplicationUser> appUsers = new HashSet<ApplicationUser>();
                appUsers.add(appUser);
                app.setApplicationUsers(appUsers);
                applicationSet.add(app);

                genericDao.save(userEntity);

                ApplicationUserId id = new ApplicationUserId(userEntity.getUserId(), appid);
                appUser.setId(id);
                appUser.setFirstRegisterTime(new Date());

                for (ApplicationUser appUserInternal : app.getApplicationUsers())
                    genericDao.save(appUserInternal);
            }

            return userEntity.getUserId();

        } catch (NamingException e) {
            e.printStackTrace();
        }
        return 0;
    }



    public static com.podcastmodern.entity.User verifyGoogleToken(String googleToken) throws
        GeneralSecurityException, IOException {

        com.podcastmodern.entity.User user = new com.podcastmodern.entity.User();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
            new JacksonFactory())
            .setAudience(Arrays.asList("634509108343-76kakqljdaci0q6d77vdhlrrkglo0d6v.apps.googleusercontent.com"))
            .setIssuer("https://accounts.google.com")
            .build();

        GoogleIdToken idToken = verifier.verify(googleToken);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            String userId = payload.getSubject();
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");
            user.setName(name);
            user.setGoogleToken(userId);
            return user;
        } else {
            return null;
        }
    }
}