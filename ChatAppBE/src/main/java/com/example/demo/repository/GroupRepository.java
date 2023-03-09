package com.example.demo.repository;

import com.example.demo.entity.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupChat, Long> {
    @Query(value = "SELECT g FROM GroupChat g JOIN g.members m JOIN m.user u WHERE u.id=?1")
    List<GroupChat> getAllByUser(Long userId);
}
