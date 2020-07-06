package com.nckpop.mychat.service;

import com.nckpop.mychat.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WebUserService {

    List<UserDto> getUsers();

}
