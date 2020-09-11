package com.thoughtworks.capability.gtb.restfulapidesign.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int id;

    private String name;

    private Gender gender;

    private String note;


    public static enum Gender {
        MALE, FEMALE;
    }
}
