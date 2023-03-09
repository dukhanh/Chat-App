package com.example.demo.config.mapper;

import com.example.demo.dto.GroupDetailDTO;
import com.example.demo.dto.GroupMemberDTO;
import com.example.demo.entity.GroupChat;
import com.example.demo.entity.GroupMember;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigModelMapper {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);


//        TypeMap<GroupChat, GroupDetailDTO> groupDetail = modelMapper.createTypeMap(GroupChat.class, GroupDetailDTO.class);
//        groupDetail.addMapping(GroupChat::getMembers, GroupDetailDTO::setMembers);

//        logDateMapper.addMapping(LogDate::getCheckin, LogDateDTO::setStartTime);
//        logDateMapper.addMapping(LogDate::getCheckout, LogDateDTO::setFinishTime);

//        TypeMap<User, UserDTO> userMapper = modelMapper.createTypeMap(User.class, UserDTO.class);
//        userMapper.addMapping(User::getRoles, UserDTO::setRoles);


        return modelMapper;
    }
}
