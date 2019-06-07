package com.ttn.reap.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ttn.reap.enums.Badge;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NamedNativeQueries({
        @NamedNativeQuery(name = "Recognition.getAllRecognitions",query = "select * from recognition e where e.recognizer_employee_id_id= :id or e.recognizee_id_id= :id",resultClass = Recognition.class),
        @NamedNativeQuery(name = "Recognition.fetchShared",query = "select * from recognition e where e.recognizer_employee_id_id= :id",resultClass = Recognition.class),
        @NamedNativeQuery(name = "Recognition.fetchReceived",query = "select * from recognition e where e.recognizee_id_id= :id",resultClass = Recognition.class)
})
public class Recognition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Employee recognizeeId;

    @OneToOne
    private Employee recognizerEmployeeId;

    private String message;

    @CreationTimestamp
    private Date dateOfRecognition;

    @Enumerated(EnumType.STRING)
    private Badge badge;

    private Boolean active=true;
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    public Integer getId() {
        return id;
    }
    public Employee getRecognizeeId() {
        return recognizeeId;
    }

    public void setRecognizeeId(Employee recognizeeId) {
        this.recognizeeId = recognizeeId;
    }
    public Employee getRecognizerEmployeeId() {
        return recognizerEmployeeId;
    }

    public void setRecognizerEmployeeId(Employee recognizerEmployeeId) {
        this.recognizerEmployeeId = recognizerEmployeeId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public Date getDateOfRecognition() {
        return dateOfRecognition;
    }

    public void setDateOfRecognition(Date dateOfRecognition) {
        this.dateOfRecognition = dateOfRecognition;
    }
    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public Recognition() {
    }

    @Override
    public String toString() {
        return "Recognition{" +
                "id=" + id +
                ", recognizeeId=" + recognizeeId +
                ", recognizerEmployeeId=" + recognizerEmployeeId +
                ", message='" + message + '\'' +
                ", dateOfRecognition=" + dateOfRecognition +
                ", badge=" + badge +
                '}';
    }
}