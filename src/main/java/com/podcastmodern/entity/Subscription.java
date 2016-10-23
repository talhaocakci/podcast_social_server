/*
 * Copyright 2003-2016 Monitise Group Limited. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Monitise Group Limited.
 * Any reproduction of this material must contain this notice.
 */
package com.podcastmodern.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "subscription", catalog = "podcastmodern")
public class Subscription {

    private Long subscriptionId;
    private String subscriptionItem;
    private Application application;
    private User user;
    private Date subscriptionDate;
    private Date trialEndDate;
    private String refreshPeriod;
    private Integer refreshCount;
    private String memberType;
    private String price;
    private String currency;


    public Subscription() {
    }

    public Subscription(Long subscriptionId, String subscriptionItem, Application application, User user, Date
        subscriptionDate, Date trialEndDate, String refreshPeriod, Integer refreshCount, String memberType, String
        price, String currency) {
        this.subscriptionId = subscriptionId;
        this.subscriptionItem = subscriptionItem;
        this.application = application;
        this.user = user;
        this.subscriptionDate = subscriptionDate;
        this.trialEndDate = trialEndDate;
        this.refreshPeriod = refreshPeriod;
        this.refreshCount = refreshCount;
        this.memberType = memberType;
        this.price = price;
        this.currency = currency;
    }

    @Id
    @GeneratedValue
    @Column(name = "subscription_id")
    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    @ManyToOne
    @JoinColumn(name = "application_id", referencedColumnName = "application_id")
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Column(name="subscription_date")
    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
    @Column(name="member_type")
    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name="subscription_item")
    public String getSubscriptionItem() {
        return subscriptionItem;
    }

    public void setSubscriptionItem(String subscriptionItem) {
        this.subscriptionItem = subscriptionItem;
    }

    @Column(name="trial_end_date")
    public Date getTrialEndDate() {
        return trialEndDate;
    }

    public void setTrialEndDate(Date trialEndDate) {
        this.trialEndDate = trialEndDate;
    }

    @Column(name="refresh_period")
    public String getRefreshPeriod() {
        return refreshPeriod;
    }

    public void setRefreshPeriod(String refreshPeriod) {
        this.refreshPeriod = refreshPeriod;
    }

    @Column(name="refresh_count")
    public Integer getRefreshCount() {
        return refreshCount;
    }

    public void setRefreshCount(Integer refreshCount) {
        this.refreshCount = refreshCount;
    }

    @Column(name="price")
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Column(name="currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


}
