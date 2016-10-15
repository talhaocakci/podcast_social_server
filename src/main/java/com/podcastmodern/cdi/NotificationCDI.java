package com.podcastmodern.cdi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.podcastmodern.dao.ApplicationDao;
import com.podcastmodern.entity.Application;
import com.podcastmodern.entity.ApplicationUser;
import com.podcastmodern.entity.Podcast;
import com.podcastmodern.entity.User;

@Named
@SessionScoped
public class NotificationCDI implements Serializable{

	public static final String API_KEY = "AIzaSyB9pQkS350q9XA1eg4y5KIs8UnbcqNlW_g";
	
	private String message;
	private List<Application> applicationList;
	private Application selectedApplication;
	private Podcast selectedPodcast;
	private String type;
	private String webPage;
	
	private List <Podcast> podcastList;
	
	@Inject
	private ApplicationDao applicationDao;
	

	
	
	
	@PostConstruct
	public void init(){
		
		applicationList = applicationDao.findAll();
	}
	
	public void updatePodcasts(){
		podcastList = new ArrayList<Podcast>();
		podcastList.addAll(selectedApplication.getPodcasts());
		
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
			  
	            
	            
	            Notification n = new Notification();
	            NotificationData nd = new NotificationData();
	            n.data = nd;
	            
	            nd.message = message;
	            nd.type = type;
	            if(type.equals("web"))
	            nd.url = webPage;
	            else
	            	nd.url = selectedPodcast.getItunesUrl() != null && !selectedPodcast.equals("") ? selectedPodcast.getItunesUrl() : selectedPodcast.getOtherRssUrl();
	            nd.podcast= selectedPodcast;
	            n.to = gcmId;
	           
	            Gson gson = new Gson();
	           

	            // Create connection to send GCM Message request.
	            URL url = new URL("https://android.googleapis.com/gcm/send");
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestProperty("Authorization", "key=" + API_KEY);
	            conn.setRequestProperty("Content-Type", "application/json");
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);

	            // Send GCM message content.
	            OutputStream outputStream = conn.getOutputStream();
	            outputStream.write(gson.toJson(n).getBytes());

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
	
	
	public Podcast getSelectedPodcast() {
		return selectedPodcast;
	}

	public void setSelectedPodcast(Podcast selectedPodcast) {
		this.selectedPodcast = selectedPodcast;
	}

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getWebPage() {
		return webPage;
	}


	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}





	public List<Podcast> getPodcastList() {
		return podcastList;
	}

	public void setPodcastList(List<Podcast> podcastList) {
		this.podcastList = podcastList;
	}





	class Notification{
		String to;
		@SerializedName(value="data")
		NotificationData data;
		
	}
	
	
	
	class NotificationData{
		String message;
		String type;
		String url;
		Podcast podcast;
		
	}
	
}
