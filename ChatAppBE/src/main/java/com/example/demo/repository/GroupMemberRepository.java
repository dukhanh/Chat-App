package com.example.demo.repository;

import com.example.demo.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    @Query("SELECT gm FROM GroupMember gm WHERE gm.user.id=?2 AND gm.group.id=?1")
    Optional<GroupMember> findUserInGroup(Long groupId, Long userId);

    @Query("DELETE FROM GroupMember gm WHERE gm.group.id=?1 AND gm.user.id=?2")
    void removeGroupMember(Long groupId, Long userId);
}
