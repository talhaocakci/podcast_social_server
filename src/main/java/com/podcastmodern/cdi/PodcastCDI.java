package com.podcastmodern.cdi;

import com.podcastmodern.dao.PodcastService;
import com.podcastmodern.entity.Podcast;
import org.springframework.util.StringUtils;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;


@Named
@SessionScoped
public class PodcastCDI implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Inject
    PodcastService podcastService;
    private Podcast newPodcast = new Podcast();

    public void addPodcast() {

        String msg = "";
        if (StringUtils.isEmpty(newPodcast.getName())) {
            msg = "Name can not be null";

        }
        if (StringUtils.isEmpty(newPodcast.getItunesUrl()) && StringUtils.isEmpty(newPodcast.getOtherRssUrl())) {
            msg = "At least one url should be given";

        }

        if (!StringUtils.isEmpty(newPodcast.getItunesUrl()) && !newPodcast.getItunesUrl().startsWith("http://") &&
            !newPodcast.getItunesUrl().startsWith("https://")) {
            msg = "Itunes URL should start with http:// or https://";
        }

        if (!StringUtils.isEmpty(newPodcast.getOtherRssUrl()) && !newPodcast.getOtherRssUrl().startsWith("http://")
            && !newPodcast.getOtherRssUrl().startsWith("https://")) {
            msg = "Other URL should start with http:// or https://";
        }


        if (!StringUtils.isEmpty(msg)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            return;
        }

        podcastService.savePodcast(newPodcast);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Podcast " +
            "added successfully", "Podcast added successfully"));

    }


    public Podcast getNewPodcast() {
        return newPodcast;
    }


    public void setNewPodcast(Podcast newPodcast) {
        this.newPodcast = newPodcast;
    }


}
