package com.podcastmodern.entity;
// Generated Nov 18, 2015 12:40:38 PM by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ApplicationPodcast generated by hbm2java
 */
@Entity
@Table(name = "application_podcast", catalog = "podcastmodern")
public class ApplicationPodcast implements java.io.Serializable {

    private ApplicationPodcastId id;
    private Application application;

    public ApplicationPodcast() {
    }

    public ApplicationPodcast(ApplicationPodcastId id, Application application) {
        this.id = id;
        this.application = application;
    }

    @EmbeddedId

    @AttributeOverrides({
        @AttributeOverride(name = "applicationId", column = @Column(name = "application_id", nullable = false)),
        @AttributeOverride(name = "podcastId", column = @Column(name = "podcast_id", nullable = false))})
    public ApplicationPodcastId getId() {
        return this.id;
    }

    public void setId(ApplicationPodcastId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false, insertable = false, updatable = false)
    public Application getApplication() {
        return this.application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

}
