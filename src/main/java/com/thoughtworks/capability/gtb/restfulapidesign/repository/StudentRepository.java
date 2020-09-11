package com.thoughtworks.capability.gtb.restfulapidesign.repository;

import com.sun.tools.javac.jvm.Gen;
import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
        return new ArrayList<>(students);
    }

    public void setStudents(List<Student> students) {
        this.students = new ArrayList<>(students);
    }

    public AtomicInteger getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(AtomicInteger idGenerator) {
        this.idGenerator = idGenerator;
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

    public List<Student> findStudentByGender(Student.Gender gender) {
        return students.stream().filter(student -> student.getGender().equals(gender)).collect(Collectors.toList());
    }

    public Optional<Student> findStudent(int id) {
        return students.stream().filter(student -> student.getId() == id).findFirst();
    }

    public Student updateStudent(int id, Student student) {
        Iterator<Student> iterator = students.iterator();
        Student updatedStudent = students.stream().filter(s -> s.getId() == id).findFirst().get();

        Student.Gender newGender = student.getGender() == null ? updatedStudent.getGender() : student.getGender();
        String newName = student.getName() == null ? updatedStudent.getName() : student.getName();
        String newNote = student.getNote() == null ? updatedStudent.getNote() : student.getNote();
        updatedStudent.setGender(newGender);
        updatedStudent.setName(newName);
        updatedStudent.setNote(newNote);

        return updatedStudent;
    }
}
