package com.thoughtworks.capability.gtb.restfulapidesign.exception;

public class TeamNotExistedException extends RuntimeException{
    public TeamNotExistedException() {
        super("Team not existed");
    }
}
