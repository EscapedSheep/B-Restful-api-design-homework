package com.thoughtworks.capability.gtb.restfulapidesign.api;

import com.thoughtworks.capability.gtb.restfulapidesign.domain.Team;
import com.thoughtworks.capability.gtb.restfulapidesign.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@V1Controller
public class TeamController {
    private TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/teams/group")
    public List<Team> groupStudentToTeam() {
        return teamService.groupStudentToTeam();
    }

    @PutMapping("/teams/{id}")
    public Team updateTeamName(@PathVariable int id, @RequestBody Team team) {
        return teamService.updateTeamName(id, team);
    }

    @GetMapping("/teams")
    public List<Team> getTeams() {
        return teamService.getTeams();
    }
}
