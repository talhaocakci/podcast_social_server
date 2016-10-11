package com.podcastmodern.cdi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.json.JSONObject;

import com.amazonaws.util.IOUtils;
import com.podcastmodern.dao.ApplicationDao;
import com.podcastmodern.entity.Application;
import com.podcastmodern.entity.ApplicationUser;
import com.podcastmodern.entity.User;

@Named
@SessionScoped
public class NotificationCDI implements Serializable{

	public static final String API_KEY = "AIzaSyB9pQkS350q9XA1eg4y5KIs8UnbcqNlW_g";
	
	private String message;
	private List<Application> applicationList;
	private Application selectedApplication;
	
	@Inject
	private ApplicationDao applicationDao;
	
	
	
	@PostConstruct
	public void init(){
		
		applicationList = applicationDao.findAll();
	}
	
	
	public String sendMessage(){
	
		Set<User> users =  new HashSet<User>();
		users.addAll(applicationDao.findusersOfApp(selectedApplication.getApplicationId()));
				
		
		for(User user : users){
			System.out.println(user.getGcmToken());
			sendGcmMessage(user.getGcmToken());
		}
		
		
		return "";
	}
	
	private void sendGcmMessage(String gcmId){
		  try {
	            // Prepare JSON containing the GCM message content. What to send and where to send.
	            JSONObject jGcmData = new JSONObject();
	            JSONObject jData = new JSONObject();
	            jData.put("message", message);
	            // Where to send GCM message.
	           
	            jGcmData.put("to", gcmId);
	            
	            // What to send in GCM message.
	            jGcmData.put("data", jData);

	            // Create connection to send GCM Message request.
	            URL url = new URL("https://android.googleapis.com/gcm/send");
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestProperty("Authorization", "key=" + API_KEY);
	            conn.setRequestProperty("Content-Type", "application/json");
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);

	            // Send GCM message content.
	            OutputStream outputStream = conn.getOutputStream();
	            outputStream.write(jGcmData.toString().getBytes());

	            // Read GCM response.
	            InputStream inputStream = conn.getInputStream();
	            String resp = IOUtils.toString(inputStream);
	            System.out.println(resp);
	            
	        } catch (IOException e) {
	            System.out.println("Unable to send GCM message.");
	            System.out.println("Please ensure that API_KEY has been replaced by the server " +
	                    "API key, and that the device's registration token is correct (if specified).");
	            e.printStackTrace();
	        }
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public List<Application> getApplicationList() {
		return applicationList;
	}


	public void setApplicationList(List<Application> applicationList) {
		this.applicationList = applicationList;
	}


	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}


	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}


	public Application getSelectedApplication() {
		return selectedApplication;
	}


	public void setSelectedApplication(Application selectedApplication) {
		this.selectedApplication = selectedApplication;
	}



	
	
}
