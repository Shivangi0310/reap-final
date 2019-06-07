package com.ttn.reap.exception;

import com.ttn.reap.enums.ExceptionStatus;

public class EmployeeException extends Exception
{
    private ExceptionStatus exceptionStatus;
    private String errorMessage;

    public EmployeeException(String msg){
        super(msg);
        this.errorMessage = msg;
        this.exceptionStatus = ExceptionStatus.DEFAULT_EXCEPTION;
    }

    public EmployeeException(String msg,ExceptionStatus status){
        super(msg);
        this.errorMessage = msg;
        this.exceptionStatus = status;
    }

    public ExceptionStatus getExceptionStatus() {
        return exceptionStatus;
    }

    public void setExceptionStatus(ExceptionStatus exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
