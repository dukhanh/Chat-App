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
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

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
}
