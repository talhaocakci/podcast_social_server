package com.podcastmodern.dao;

import com.podcastmodern.cdi.FileUploadCDI;
import com.podcastmodern.entity.Podcast;
import com.podcastmodern.service.FileUploadService;

import javax.inject.Inject;
import java.io.Serializable;


public class PodcastService implements Serializable {


    @Inject
    GenericDao genericDao;

    @Inject
    FileUploadCDI fileUpladCDI;

    @Inject
    FileUploadService fileUploadService;

    public void savePodcast(Podcast podcast) {

        //	fileUploadService.doUpload(podcast.getName() +"cover", fileUpladCDI.getFile().getContents());

        genericDao.save(podcast);

    }

}
