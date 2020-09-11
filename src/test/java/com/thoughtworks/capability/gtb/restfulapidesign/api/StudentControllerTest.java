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
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        mockMvc.perform(post("/v1/students").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(student.getName())));

        List<Student> students = studentRepository.getStudents();
        assertEquals(students.size(), 1);
        assertEquals(students.get(0).getName(), student.getName());
    }

    @Test
    void should_delete_student_when_call_api_given_student_id() throws Exception {
        student = studentRepository.addStudent(student);

        mockMvc.perform(delete("/v1/students/" + student.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_return_intend_student_when_call_api_given_search_criteria() throws Exception {
        student = studentRepository.addStudent(student);
        Student newFemaleStudent = Student.builder()
                .gender(Student.Gender.FEMALE)
                .name("Simon")
                .note("")
                .build();
        newFemaleStudent = studentRepository.addStudent(newFemaleStudent);

        mockMvc.perform((get("/v1/students")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(student.getName())))
                .andExpect(jsonPath("$[1].name", is(newFemaleStudent.getName())));

        mockMvc.perform((get("/v1/students").param("gender", "MALE")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(student.getName())));

        mockMvc.perform((get("/v1/students").param("gender", "FEMALE")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(newFemaleStudent.getName())));
    }

    @Test
    void should_return_student_when_call_api_given_student_id() throws Exception {
        student = studentRepository.addStudent(student);

        mockMvc.perform(get("/v1/students/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(student.getName())));
    }

    @Test
    void should_update_student_given_id_and_student_info() throws Exception {
        student = studentRepository.addStudent(student);

        String newName = "newName";
        String newNote = "newNote";
        Student newStudent = Student.builder().name(newName).note(newNote).build();

        String json = objectMapper.writeValueAsString(newStudent);

        mockMvc.perform(put("/v1/students/" + student.getId()).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(student.getName())))
                .andExpect(jsonPath("$.note", is(student.getNote())));

        Student updatedStudent = studentRepository.getStudents().stream().filter(s -> s.getId() == student.getId()).findFirst().get();
        assertEquals(student.getName(), updatedStudent.getName());
        assertEquals(student.getNote(), updatedStudent.getNote());
    }
}