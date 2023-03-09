package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessage extends Message {
    @ManyToOne
    @JoinColumn(name="groupId")
    private GroupChat group;


    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

}
