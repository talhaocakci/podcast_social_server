package com.podcastmodern.cdi;

import org.primefaces.model.UploadedFile;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class FileUploadCDI implements Serializable {


    private UploadedFile file;

    public static void main(String[] args) {

        double y = (1.018488 - 22.61071) / (1 + Math.pow((1.55 / 1.684687), 32.90776)) + 22.61071;
        System.out.println(y);
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
        if (file != null) {

            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

}
