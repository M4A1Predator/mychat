package com.nckpop.mychat.service;

import com.nckpop.mychat.entity.CustomUser;
import com.nckpop.mychat.model.UserDto;
import com.nckpop.mychat.repository.UserRepository;
import com.nckpop.mychat.util.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WebUserServiceImpl implements WebUserService {

    @Autowired
    private UserRepository userRepository;

    @Resource
    private UserRequest userRequest;

    @Override
    public List<UserDto> getUsers() {
        List<CustomUser> users = userRepository.findAllExceptSelf(userRequest.getUserId());
        return users.stream().map(u -> {
            UserDto userDto = new UserDto();
            userDto.setUserId(u.get_id().toString());
            userDto.setUsername(u.getUsername());
            return userDto;
        }).collect(Collectors.toList());
    }
}
