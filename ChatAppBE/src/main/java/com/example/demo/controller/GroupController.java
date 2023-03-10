package com.example.demo.controller;

import com.example.demo.common.message.MessageContext;
import com.example.demo.dto.GroupDTO;
import com.example.demo.dto.GroupDetailDTO;
import com.example.demo.dto.GroupMemberDTO;
import com.example.demo.payload.CreateGroupReq;
import com.example.demo.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("")
    public ResponseEntity<GroupDTO> createGroup(@RequestBody CreateGroupReq group) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GroupDTO result = this.groupService.createGroup(username, group.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupId) {
        return null;
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDetailDTO> getGroup(@PathVariable Long groupId) {
        GroupDetailDTO result = this.groupService.getGroupDetail(groupId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<GroupDTO>> getAllGroupByUser(@PathVariable Long userId) {
        List<GroupDTO> result = this.groupService.getAllGroupByUser(userId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("members/{groupId}/{userId}")
    public ResponseEntity<GroupMemberDTO> addMember(@PathVariable Long groupId, @PathVariable Long userId) {
        GroupMemberDTO result = this.groupService.addUserToGroup(groupId, userId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("members/{groupId}/{userId}")
    public ResponseEntity<?> deleteMember(@PathVariable Long groupId, @PathVariable Long userId) {
        this.groupService.removeUserFromGroup(groupId, userId);
        return ResponseEntity.ok(MessageContext.DELETE_MEMBER_SUCCESS);
    }

    @DeleteMapping("leave/{groupId}")
    public ResponseEntity<?> deleteMember(@PathVariable Long groupId) {
        this.groupService.leaveGroup(groupId);
        return ResponseEntity.ok(MessageContext.LEAVE_GROUP_SUCCESS);
    }

    @PutMapping("members/{groupId}/{userId}")
    public ResponseEntity<GroupMemberDTO> updateRoleGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        GroupMemberDTO result = this.groupService.assignRole(groupId, userId);
        return ResponseEntity.ok(result);
    }
}
