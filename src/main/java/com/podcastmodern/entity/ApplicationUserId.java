package com.podcastmodern.entity;
// Generated Nov 18, 2015 1:35:40 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ApplicationUserId generated by hbm2java
 */
@Embeddable
public class ApplicationUserId implements java.io.Serializable {

	private int userId;
	private int applicationId;

	public ApplicationUserId() {
	}

	public ApplicationUserId(int userId, int applicationId) {
		this.userId = userId;
		this.applicationId = applicationId;
	}

	@Column(name = "user_id", nullable = false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "application_id", nullable = false)
	public int getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ApplicationUserId))
			return false;
		ApplicationUserId castOther = (ApplicationUserId) other;

		return (this.getUserId() == castOther.getUserId()) && (this.getApplicationId() == castOther.getApplicationId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getUserId();
		result = 37 * result + this.getApplicationId();
		return result;
	}

}