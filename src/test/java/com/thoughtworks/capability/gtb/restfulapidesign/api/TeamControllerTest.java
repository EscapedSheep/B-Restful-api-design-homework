package com.thoughtworks.capability.gtb.restfulapidesign.api;

import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import com.thoughtworks.capability.gtb.restfulapidesign.repository.StudentRepository;
import com.thoughtworks.capability.gtb.restfulapidesign.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        teamRepository.setTeams(new ArrayList<>());
        studentRepository.setStudents(new ArrayList<>());
    }

    @Test
    void should_get_group_team_when_call_api() throws Exception {
        for (int i = 1; i <= 8; i++) {
            studentRepository.addStudent(Student.builder().name("student" + i).build());
        }

        mockMvc.perform(get("/v1/teams/group"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].name", is("Team 1")))
                .andExpect(jsonPath("$[0].studentList", hasSize(2)))
                .andExpect(jsonPath("$[1].name", is("Team 2")))
                .andExpect(jsonPath("$[1].studentList", hasSize(2)))
                .andExpect(jsonPath("$[2].name", is("Team 3")))
                .andExpect(jsonPath("$[2].studentList", hasSize(1)))
                .andExpect(jsonPath("$[3].name", is("Team 4")))
                .andExpect(jsonPath("$[3].studentList", hasSize(1)))
                .andExpect(jsonPath("$[4].name", is("Team 5")))
                .andExpect(jsonPath("$[4].studentList", hasSize(1)))
                .andExpect(jsonPath("$[5].name", is("Team 6")))
                .andExpect(jsonPath("$[5].studentList", hasSize(1)));
    }
}