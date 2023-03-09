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
public class UserMessage extends Message{
    @ManyToOne
    @JoinColumn(name="sourceId")
    private User sourceUser;

    @ManyToOne
    @JoinColumn(name="targetId")
    private User targetUser;

}
