package com.podcastmodern.rs.endpoints;

public class PodcastData {

    private String duration;
    private String title;
    private String description;

    public PodcastData(String duration, String title, String description) {
        this.duration = duration;
        this.title = title;
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
