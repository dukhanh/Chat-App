package com.example.demo.service;

import com.example.demo.dto.GroupDTO;
import com.example.demo.dto.GroupDetailDTO;
import com.example.demo.entity.GroupChat;

import java.util.List;

public interface GroupService {
   List<GroupDTO> getAllGroupByUser(Long userId);

   GroupDTO createGroup(String username, String groupName);

   GroupDetailDTO getGroupDetail(Long groupId);
}
