package com.podcastmodern.cdi;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.podcastmodern.dao.PodcastService;
import com.podcastmodern.entity.Podcast;


@Named
@SessionScoped
public class PodcastCDI implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Podcast newPodcast  = new Podcast();
	
	@Inject
	PodcastService podcastService;
	
	
	public void addPodcast(){
		
		podcastService.savePodcast(newPodcast);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Application added","Application added"));
		
	}



	public Podcast getNewPodcast() {
		return newPodcast;
	}



	public void setNewPodcast(Podcast newPodcast) {
		this.newPodcast = newPodcast;
	}
	
	

}
