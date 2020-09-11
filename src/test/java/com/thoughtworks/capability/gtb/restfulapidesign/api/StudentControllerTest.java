package com.thoughtworks.capability.gtb.restfulapidesign.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import com.thoughtworks.capability.gtb.restfulapidesign.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private Student student;
    
    @BeforeEach
    void setUp() {
        studentRepository.setStudents(new ArrayList<>());
        objectMapper = new ObjectMapper();
        student = Student.builder()
                .gender(Student.Gender.MALE)
                .name("tom")
                .note("")
                .build();
    }

    @Test
    void should_add_student_when_call_api_given_student() throws Exception {
        String json = objectMapper.writeValueAsString(student);
        mockMvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(student.getName())));

        List<Student> students = studentRepository.getStudents();
        assertEquals(students.size(), 1);
        assertEquals(students.get(0).getName(), student.getName());
    }
}