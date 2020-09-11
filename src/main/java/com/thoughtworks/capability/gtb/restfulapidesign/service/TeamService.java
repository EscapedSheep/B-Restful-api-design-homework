package com.thoughtworks.capability.gtb.restfulapidesign.service;

import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import com.thoughtworks.capability.gtb.restfulapidesign.domain.Team;
import com.thoughtworks.capability.gtb.restfulapidesign.exception.TeamNotExistedException;
import com.thoughtworks.capability.gtb.restfulapidesign.repository.StudentRepository;
import com.thoughtworks.capability.gtb.restfulapidesign.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TeamService {
    private TeamRepository teamRepository;

    private StudentRepository studentRepository;

    private final static int TEAM_COUNTS = 6;

    @Autowired
    public TeamService(TeamRepository teamRepository, StudentRepository studentRepository) {
        this.teamRepository = teamRepository;
        this.studentRepository = studentRepository;
    }

    public List<Team> groupStudentToTeam() {
        if (teamRepository.getTeams().isEmpty()) {
            initTeam();
        }
        List<Student> students = studentRepository.getStudents();
        Collections.shuffle(students);

        int currentTeamId = 1;
        for (Student student : students) {
            teamRepository.addStudentToTeam(currentTeamId, student);
            currentTeamId = currentTeamId == TEAM_COUNTS ? 1 : currentTeamId + 1;
        }
        return teamRepository.getTeams();
    }

    private void initTeam() {
        for (int i = 1; i <= TEAM_COUNTS; i++) {
            teamRepository.addTeam(Team.builder().name("Team " + i).build());
        }
    }

    public Team updateTeamName(int id, Team team) {
        if (!teamRepository.findTeam(id).isPresent()) {
            throw new TeamNotExistedException();
        }
        return teamRepository.updateTeamName(id, team);
    }

    public List<Team> getTeams() {
        return teamRepository.getTeams();
    }
}
