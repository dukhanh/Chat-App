package com.example.demo.dto;

import com.example.demo.common.enumm.RoleGroup;
import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberDTO extends UserDTO{
    private RoleGroup role;

    public GroupMemberDTO(RoleGroup role, User user){
        this.role = role;
        super.setId(user.getId());
        super.setAvatar(user.getAvatar());
        super.setEmail(user.getEmail());
        super.setFirstName(user.getFirstName());
        super.setLastName(user.getLastName());
        super.setUsername(user.getUsername());
    }
}
