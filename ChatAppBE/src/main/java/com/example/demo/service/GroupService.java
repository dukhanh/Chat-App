package com.example.demo.service;

import com.example.demo.dto.GroupDTO;
import com.example.demo.dto.GroupDetailDTO;
import com.example.demo.dto.GroupMemberDTO;

import java.util.List;

public interface GroupService {
   List<GroupDTO> getAllGroupByUser(Long userId);

   GroupDTO createGroup(String username, String groupName);

   GroupDetailDTO getGroupDetail(Long groupId);

   GroupMemberDTO addUserToGroup(Long groupId, Long userId);

   void removeUserFromGroup(Long groupId, Long userId);

   void leaveGroup(Long groupId);

   GroupMemberDTO assignRole(Long groupId, Long userId);
}
