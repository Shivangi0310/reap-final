package com.ttn.reap.DTO;

public class RevokeRecognitionDto {
    int recognitionId;
    String revokeReason;

    public int getRecognitionId() {
        return recognitionId;
    }

    public void setRecognitionId(int recognitionId) {
        this.recognitionId = recognitionId;
    }

    public String getRevokeReason() {
        return revokeReason;
    }

    public void setRevokeReason(String revokeReason) {
        this.revokeReason = revokeReason;
    }
}
