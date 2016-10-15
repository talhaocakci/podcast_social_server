package com.podcastmodern.entity;
// Generated Nov 18, 2015 1:35:40 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserCloudmessagingId generated by hbm2java
 */
@Embeddable
public class UserCloudmessagingId implements java.io.Serializable {

    private int messagingId;
    private int userId;

    public UserCloudmessagingId() {
    }

    public UserCloudmessagingId(int messagingId, int userId) {
        this.messagingId = messagingId;
        this.userId = userId;
    }

    @Column(name = "messaging_id", nullable = false)
    public int getMessagingId() {
        return this.messagingId;
    }

    public void setMessagingId(int messagingId) {
        this.messagingId = messagingId;
    }

    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof UserCloudmessagingId))
            return false;
        UserCloudmessagingId castOther = (UserCloudmessagingId) other;

        return (this.getMessagingId() == castOther.getMessagingId()) && (this.getUserId() == castOther.getUserId());
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getMessagingId();
        result = 37 * result + this.getUserId();
        return result;
    }

}
