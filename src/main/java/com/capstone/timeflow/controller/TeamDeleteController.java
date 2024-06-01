package com.capstone.timeflow.controller;

import com.capstone.timeflow.entity.TeamEntity;
import com.capstone.timeflow.repository.RoleRepository;
import com.capstone.timeflow.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team/delete")
public class TeamDeleteController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private RoleRepository roleRepository;

    @DeleteMapping("/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long teamId) {
        System.out.println("여까진 되는덧ㅅ~~");
        TeamEntity teamEntity = teamService.getTeamByTeamId(teamId); // TeamService에서 teamId로 TeamEntity를 가져옵니다.
        roleRepository.deleteByTeamId(teamEntity);
        teamService.deleteTeam(teamId);
        System.out.println("delete" + teamId);
        return ResponseEntity.ok().build();
    }

}