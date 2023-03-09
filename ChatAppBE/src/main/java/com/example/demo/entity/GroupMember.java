package com.example.demo.entity;

import com.example.demo.common.enumm.RoleGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {
    @EmbeddedId
    private GroupMemberPk id = new GroupMemberPk();

    @Enumerated(value = EnumType.STRING)
    private RoleGroup role;

    @ManyToOne
    @JoinColumn(name = "groupId")
    @MapsId("groupId")
    private GroupChat group;

    @ManyToOne
    @JoinColumn(name = "userId")
    @MapsId("userId")
    private User user;

}

@Embeddable
class GroupMemberPk implements Serializable {
    private Long userId;
    private Long groupId;
}
