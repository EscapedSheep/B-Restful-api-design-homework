package com.thoughtworks.capability.gtb.restfulapidesign.exception;

public class StudentNotExistedException extends RuntimeException{
    public StudentNotExistedException() {
        super("Student not existed");
    }
}
