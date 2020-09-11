package com.thoughtworks.capability.gtb.restfulapidesign.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import com.thoughtworks.capability.gtb.restfulapidesign.domain.Team;
import com.thoughtworks.capability.gtb.restfulapidesign.repository.StudentRepository;
import com.thoughtworks.capability.gtb.restfulapidesign.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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
        teamRepository.setIdGenerator(new AtomicInteger(1));
        studentRepository.setStudents(new ArrayList<>());
        studentRepository.setIdGenerator(new AtomicInteger(0));
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

    @Test
    void should_update_team_name_given_team_id_and_new_name() throws Exception{
        Team team = Team.builder().name("newTeam").build();
        team = teamRepository.addTeam(team);
        String changedName = "changedName";
        Team newTeam = Team.builder().name(changedName).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(newTeam);
        mockMvc.perform(put("/v1/teams/" + team.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(changedName)));
    }

    @Test
    void should_return_all_team() throws Exception {
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

        mockMvc.perform(get("/v1/teams"))
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