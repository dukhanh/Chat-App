package com.example.demo.controller;

import com.example.demo.dto.GroupDTO;
import com.example.demo.dto.GroupDetailDTO;
import com.example.demo.entity.GroupChat;
import com.example.demo.payload.CreateGroupReq;
import com.example.demo.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (value = "/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("")
    public ResponseEntity<?> createGroup(@RequestBody CreateGroupReq group){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GroupDTO result = this.groupService.createGroup(username, group.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupId){
        return null;
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<?> getGroup(@PathVariable Long groupId){
        GroupDetailDTO result = this.groupService.getGroupDetail(groupId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<?> getAllGroupByUser(@PathVariable Long userId){
        List<GroupDTO> result = this.groupService.getAllGroupByUser(userId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("members/{groupId}/{userId}")
    public ResponseEntity<?> addMember(@PathVariable Long groupId, @PathVariable Long userId){
        return null;
    }

    @DeleteMapping("members/{groupId}/{userId}")
    public ResponseEntity<?> deleteMember(@PathVariable Long groupId, @PathVariable Long userId){
        return null;
    }

    @PutMapping("members/{groupId}/{userId}")
    public ResponseEntity<?> updateRoleGroup(@PathVariable Long groupId, @PathVariable Long userId){
        return null;
    }
}
