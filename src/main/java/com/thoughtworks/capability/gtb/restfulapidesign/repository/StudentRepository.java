package com.thoughtworks.capability.gtb.restfulapidesign.repository;

import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class StudentRepository {
    private List<Student> students;
    private AtomicInteger idGenerator;

    @PostConstruct
    private void init() {
        students = new ArrayList<>();
        idGenerator = new AtomicInteger(0);
    }

}
