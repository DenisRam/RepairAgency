package com.training.model.exeptions;

public class DataBaseException extends RuntimeException {

    public DataBaseException(){

    }

    public DataBaseException(String message){
        super(message);
    }
}
