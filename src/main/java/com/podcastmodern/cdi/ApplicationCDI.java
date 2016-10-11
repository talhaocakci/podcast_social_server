package com.podcastmodern.cdi;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.podcastmodern.dao.ApplicationDao;
import com.podcastmodern.dao.PodcastDao;
import com.podcastmodern.entity.Application;
import com.podcastmodern.entity.Podcast;

@Named
@SessionScoped
public class ApplicationCDI implements Serializable{

	@Inject
	private PodcastPickListView podcastPickListView;
	
	@Inject
	private ApplicationDao applicationDao;
	
	private Application newApplication = new Application();
	
	
	public String saveApplication(){
		
		List<Podcast> selectedPodcasts = podcastPickListView.getPodcastsDualList().getTarget();
		
		HashSet<Podcast> podcastSet = new HashSet<Podcast>();
		podcastSet.addAll(selectedPodcasts);
		
		newApplication.setPodcasts(podcastSet);
		
		applicationDao.save(newApplication);
		
		return "";
	}


	public PodcastPickListView getPodcastPickListView() {
		return podcastPickListView;
	}


	public void setPodcastPickListView(PodcastPickListView podcastPickListView) {
		this.podcastPickListView = podcastPickListView;
	}


	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}


	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}


	public Application getNewApplication() {
		return newApplication;
	}


	public void setNewApplication(Application newApplication) {
		this.newApplication = newApplication;
	}
	
	
	
	
}
