package com.ttn.reap.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class RevokeRecognitionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer revokeId;

    private String revokeReason;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date revokeDate;

    @OneToOne
    @JoinColumn(name="revoked_recognition")
    Recognition recognition;

    public Integer getRevokeId() {
        return revokeId;
    }

    public String getRevokeReason() {
        return revokeReason;
    }

    public void setRevokeReason(String revokeReason) {
        this.revokeReason = revokeReason;
    }

    public Date getRevokeDate() {
        return revokeDate;
    }

    public void setRevokeDate(Date revokeDate) {
        this.revokeDate = revokeDate;
    }

    public Recognition getRecognition() {
        return recognition;
    }

    public void setRecognition(Recognition recognition) {
        this.recognition = recognition;
    }

    @Override
    public String toString() {
        return "RevokeRecognitionDetails{" +
                "revokeId=" + revokeId +
                ", revokeReason='" + revokeReason + '\'' +
                ", revokeDate=" + revokeDate +
                ", recognition=" + recognition +
                '}';
    }
}
