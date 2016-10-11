package com.podcastmodern.rs.endpoints;
 
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.podcastmodern.dao.ApplicationDao;
import com.podcastmodern.dao.GenericDao;
import com.podcastmodern.entity.Application;
import com.podcastmodern.entity.ApplicationUser;
import com.podcastmodern.entity.ApplicationUserId;
import com.podcastmodern.entity.User;


 
@Controller
@RequestMapping("/application/{appid}")
public class PodcastModernAPI {
	
	
	ApplicationDao applicationDao;
	GenericDao genericDao;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Application getApplication(@PathVariable Integer appid) {

		 try {
			applicationDao = (ApplicationDao)new InitialContext().lookup("java:global/PodcastModern/ApplicationDao");
			System.out.println(applicationDao);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Application app = applicationDao.findApplicationById(appid);
		return app;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody void registerUser(@RequestParam String userString, @PathVariable Integer appid){
		try {
			
			genericDao = (GenericDao)new InitialContext().lookup("java:global/PodcastModern/GenericDao");
			
			
			User user = 
			        new ObjectMapper().readValue(userString, User.class);
			user.getName();
			
			Set<Application> applicaationSet = new HashSet<Application>();
			Set<User> userSet = new HashSet<User>();
			userSet.add(user);
			
			
			Application app = (Application)genericDao.get(Application.class, appid);
			ApplicationUser appUser = new ApplicationUser();
			appUser.setApplication(app);
			
			appUser.setUser(user);
			Set<ApplicationUser> appUsers = new HashSet<ApplicationUser>();
			appUsers.add(appUser);
			app.setApplicationUsers(appUsers);
			
			applicaationSet.add(app);
			
			genericDao.save(user);
			
			ApplicationUserId id = new ApplicationUserId(user.getUserId(), appid);
			appUser.setId(id);
			
			appUser.setFirstRegisterTime(new Date());
			for(ApplicationUser appUserInternal : app.getApplicationUsers())
				genericDao.save(appUserInternal);
			
			System.out.println(user);
			
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
}