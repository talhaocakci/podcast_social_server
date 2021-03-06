package com.podcastmodern.entity;
// Generated Nov 18, 2015 1:35:40 PM by Hibernate Tools 4.0.0

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Application generated by hbm2java
 */
@Entity
@Table(name = "application", catalog = "podcastmodern")
public class Application implements java.io.Serializable {

    private Integer applicationId;
    private String applicationName;
    private String category;
    private Set<Podcast> podcasts = new HashSet<Podcast>(0);
    private Set<ApplicationUser> applicationUsers = new HashSet<ApplicationUser>(0);

    public Application() {
    }

    public Application(String applicationName, String category, Set podcasts, Set applicationUsers) {
        this.applicationName = applicationName;
        this.category = category;
        this.podcasts = podcasts;
        this.applicationUsers = applicationUsers;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "application_id", unique = true, nullable = false)
    public Integer getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    @Column(name = "application_name", length = 50)
    public String getApplicationName() {
        return this.applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Column(name = "category", length = 50)
    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "application_podcast", catalog = "podcastmodern", joinColumns = {
        @JoinColumn(name = "application_id", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "podcast_id", nullable = false, updatable = false)})
    public Set<Podcast> getPodcasts() {
        return this.podcasts;
    }

    public void setPodcasts(Set<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "application")
    public Set<ApplicationUser> getApplicationUsers() {
        return this.applicationUsers;
    }

    public void setApplicationUsers(Set<ApplicationUser> applicationUsers) {
        this.applicationUsers = applicationUsers;
    }

    @Override
    public String toString() {
        return "Application{" + "applicationId=" + applicationId + ", applicationName=" + applicationName + '}';
    }


}
