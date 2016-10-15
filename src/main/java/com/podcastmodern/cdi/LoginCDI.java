package com.podcastmodern.cdi;

import com.podcastmodern.entity.Customer;
import com.podcastmodern.entity.CustomerHome;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;


@Named(value = "login")
@RequestScoped
public class LoginCDI implements Serializable {

    @Inject
    CustomerHome customerDao;
    @Inject
    private SessionCDI session;
    private String userName;
    private String password;


    public String login() {

        Customer c = customerDao.findByUsernameAndPassword(userName, password);
        if (c == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong " +
                "username or password", "Wrong username or password"));
            return "";
        }
        session.setCustomer(c);

        return "customerdashboard?faces-redirect=true";
    }


    public SessionCDI getSession() {
        return session;
    }

    public void setSession(SessionCDI session) {
        this.session = session;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


}
