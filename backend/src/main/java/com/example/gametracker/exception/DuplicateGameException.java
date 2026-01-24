package com.example.gametracker.exception;

public class DuplicateGameException extends RuntimeException{
    public DuplicateGameException(String message){
        super(message);
    }
}
