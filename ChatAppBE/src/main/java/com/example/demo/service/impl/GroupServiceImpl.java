package com.example.demo.service.impl;

import com.example.demo.common.enumm.RoleGroup;
import com.example.demo.common.message.MessageContext;
import com.example.demo.dto.GroupDTO;
import com.example.demo.dto.GroupDetailDTO;
import com.example.demo.dto.GroupMemberDTO;
import com.example.demo.entity.GroupChat;
import com.example.demo.entity.GroupMember;
import com.example.demo.entity.User;
import com.example.demo.repository.GroupMemberRepository;
import com.example.demo.repository.GroupRepository;
import com.example.demo.service.GroupService;
import com.example.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.MethodNotAllowedException;
import org.webjars.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final GroupMemberRepository groupMemberRepository;

    public GroupServiceImpl(GroupRepository groupRepository, ModelMapper modelMapper,
                            UserService userService, GroupMemberRepository groupMemberRepository) {
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.groupMemberRepository = groupMemberRepository;
    }

    @Override
    public List<GroupDTO> getAllGroupByUser(Long userId) {
        List<GroupChat> groups = groupRepository.getAllByUser(userId);
        return groups.stream().map(group -> this.modelMapper.map(group, GroupDTO.class)).toList();
    }

    @Override
    public GroupDTO createGroup(String username, String groupName) {
        Optional<GroupChat> isExists = this.groupRepository.findByName(groupName);
        if (isExists.isPresent()) {
            throw new IllegalArgumentException(MessageContext.EXIST_GROUP);
        }
        User userOwn = this.userService.findUserByUsername(username);
        GroupChat group = new GroupChat();
        group.setName(groupName);
        group.setCreateBy(userOwn);
        group = this.groupRepository.save(group);

        GroupMember groupMember = new GroupMember();
        groupMember.setUser(userOwn);
        groupMember.setRole(RoleGroup.ADMIN);
        groupMember.setGroup(group);
        this.groupMemberRepository.save(groupMember);
        return this.modelMapper.map(group, GroupDTO.class);
    }

    @Override
    public GroupDetailDTO getGroupDetail(Long groupId) {
        GroupChat groupChat = this.groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(MessageContext.NOT_FOUND_GROUP));
        GroupDetailDTO result = this.modelMapper.map(groupChat, GroupDetailDTO.class);
        result.setMember(groupChat.getMembers().stream().map(
                member -> new GroupMemberDTO(member.getRole(), member.getUser())).toList()
        );
        return result;
    }

    @Override
    public GroupMemberDTO addUserToGroup(Long groupId, Long userId) {
        GroupChat groupChat = this.groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(MessageContext.NOT_FOUND_GROUP));
        Optional<GroupMember> isExists = this.groupMemberRepository.findUserInGroup(groupId, userId);
        if (isExists.isPresent()) {
            throw new IllegalArgumentException(MessageContext.EXIST_GROUP_MEMBER);
        }
        User user = this.userService.findUserById(userId);
        if (user == null) {
            throw new NotFoundException(MessageContext.NOT_FOUND_USER);
        }
        GroupMember member = new GroupMember();
        member.setGroup(groupChat);
        member.setUser(user);
        member.setRole(RoleGroup.MEMBER);
        member = this.groupMemberRepository.save(member);

        return new GroupMemberDTO(member.getRole(), member.getUser());
    }

    @Override
    public void removeUserFromGroup(Long groupId, Long userId) {
        User doUser = this.userService.currentLoginUser();
        GroupMember doMember = this.groupMemberRepository.findUserInGroup(groupId, doUser.getId()).orElse(null);
        if ((doMember == null) || (doMember.getRole() != RoleGroup.ADMIN)) {
            throw new MethodNotAllowedException(MessageContext.CANNOT_DELETE_MEMBER, Collections.singleton(HttpMethod.DELETE));
        }
        this.groupMemberRepository.removeGroupMember(groupId, userId);
    }

    @Override
    public void leaveGroup(Long groupId) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.findUserByUsername(username);
        this.groupMemberRepository.removeGroupMember(groupId, user.getId());
    }

    @Override
    public GroupMemberDTO assignRole(Long groupId, Long userId) {
        User doUser = this.userService.currentLoginUser();
        GroupMember doMember = this.groupMemberRepository.findUserInGroup(groupId, doUser.getId()).orElse(null);
        if ((doMember == null) || (doMember.getRole() != RoleGroup.ADMIN)) {
            throw new MethodNotAllowedException(MessageContext.CANNOT_ASSIGN_ROLE_MEMBER, Collections.singleton(HttpMethod.DELETE));
        }
        GroupMember member = this.groupMemberRepository.findUserInGroup(groupId, userId).orElseThrow(
                () -> new NotFoundException(MessageContext.NOT_FOUND_GROUP_MEMBER)
        );
        if (member.getRole() == RoleGroup.MEMBER) {
            member.setRole(RoleGroup.ADMIN);
        } else {
            member.setRole(RoleGroup.MEMBER);
        }
        member = this.groupMemberRepository.save(member);
        return new GroupMemberDTO(member.getRole(), member.getUser());
    }
}
