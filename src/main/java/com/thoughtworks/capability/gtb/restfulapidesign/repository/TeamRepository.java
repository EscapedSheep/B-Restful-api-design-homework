package com.thoughtworks.capability.gtb.restfulapidesign.repository;

import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import com.thoughtworks.capability.gtb.restfulapidesign.domain.Team;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TeamRepository {
    private List<Team> teams;

    private AtomicInteger idGenerator;

    @PostConstruct
    public void init() {
        teams = new ArrayList<>();
        idGenerator = new AtomicInteger(1);
    }

    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }

    public AtomicInteger getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(AtomicInteger idGenerator) {
        this.idGenerator = idGenerator;
    }

    public void setTeams(List<Team> teams) {
        this.teams = new ArrayList<>(teams);
    }

    public Team addTeam(Team team) {
        team.setId(idGenerator.getAndIncrement());
        teams.add(team);
        return team;
    }

    public Team addStudentToTeam(int teamId, Student student) {
       Team findTeam = teams.stream().filter(team -> team.getId() == teamId).findFirst().get();
       findTeam.addStudent(student);
       return findTeam;
    }

    public Optional<Team> findTeam(int id) {
        return teams.stream().filter(team -> team.getId() == id).findFirst();
    }

    public Team updateTeamName(int id, Team team) {
        Team findTeam = findTeam(id).get();
        findTeam.setName(team.getName());
        return findTeam;
    }
}
