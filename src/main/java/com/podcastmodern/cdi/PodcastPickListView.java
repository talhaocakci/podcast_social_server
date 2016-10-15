package com.podcastmodern.cdi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import com.podcastmodern.dao.PodcastDao;
import com.podcastmodern.entity.Podcast;

@Named
@ViewScoped
public class PodcastPickListView implements Serializable {

	@Inject 
	private PodcastDao podcastDao;
	
	List<Podcast> target = new ArrayList<Podcast>();
	List<Podcast> source = new ArrayList<Podcast>();
	
	private DualListModel<Podcast> podcastsDualList = new DualListModel<Podcast>(source, target);

    
    @PostConstruct
    public void init() {
    	List<Podcast> target = new ArrayList<Podcast>();
    	List<Podcast> source = new ArrayList<Podcast>();
        
       source = podcastDao.getAllPodcasts();
       podcastsDualList = new DualListModel<Podcast>(source, target);
       
    }


	public PodcastDao getPodcastDao() {
		return podcastDao;
	}


	public void setPodcastDao(PodcastDao podcastDao) {
		this.podcastDao = podcastDao;
	}


	public DualListModel<Podcast> getPodcastsDualList() {
		return podcastsDualList;
	}


	public void setPodcastsDualList(DualListModel<Podcast> productsDualList) {
		this.podcastsDualList = productsDualList;
	}


	
    
    
}
