package com.podcastmodern.cdi;

import com.podcastmodern.dao.PodcastDao;
import com.podcastmodern.entity.Podcast;
import org.primefaces.model.DualListModel;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class PodcastPickListView implements Serializable {

    List<Podcast> target = new ArrayList<Podcast>();
    List<Podcast> source = new ArrayList<Podcast>();
    @Inject
    private PodcastDao podcastDao;
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
