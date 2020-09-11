package com.thoughtworks.capability.gtb.restfulapidesign.repository;

import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class StudentRepository {
    private List<Student> students;
    private AtomicInteger idGenerator;

    public StudentRepository() {
    }

    @PostConstruct
    private void init() {
        students = new ArrayList<>();
        idGenerator = new AtomicInteger(0);
        Student student = Student.builder()
                .gender(Student.Gender.MALE)
                .name("tom")
                .note("")
                .build();
        addStudent(student);
    }

    public Student addStudent(Student student) {
        student.setId(idGenerator.getAndIncrement());
        students.add(student);
        return student;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void deleteStudent(int id) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student currentStudent = iterator.next();
            if (currentStudent.getId() == id) {
                iterator.remove();
            }
        }
    }
}
