package com.thoughtworks.capability.gtb.restfulapidesign.service;

import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import com.thoughtworks.capability.gtb.restfulapidesign.exception.StudentNotExistedException;
import com.thoughtworks.capability.gtb.restfulapidesign.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.addStudent(student);
    }

    public void deleteStudent(int id) {
        studentRepository.deleteStudent(id);
    }

    public List<Student> findStudents(String gender) {
        if (gender == null) {
            return studentRepository.getStudents();
        }
        Student.Gender genderObject = Student.Gender.valueOf(gender);
        return studentRepository.findStudentByGender(genderObject);
    }

    public Student findStudent(int id) {
        Optional<Student> findResult = studentRepository.findStudent(id);
        if (!findResult.isPresent()) {
            throw new StudentNotExistedException();
        }
        return findResult.get();
    }
}
