package com.ttn.reap.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RecognitionHistoryDto {
    private String recognizorName;
    private String recognizeeName;
    private String badge;
    private String message;
    private String recognizeePic;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private Date date;

    public String getRecognizorName() {
        return recognizorName;
    }

    public void setRecognizorName(String recognizorName) {
        this.recognizorName = recognizorName;
    }

    public String getRecognizeeName() {
        return recognizeeName;
    }

    public void setRecognizeeName(String recognizeeName) {
        this.recognizeeName = recognizeeName;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecognizeePic() {
        return recognizeePic;
    }

    public void setRecognizeePic(String recognizeePic) {
        this.recognizeePic = recognizeePic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
