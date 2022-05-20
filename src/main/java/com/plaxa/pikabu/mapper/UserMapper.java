package com.plaxa.pikabu.mapper;

import com.plaxa.pikabu.dto.RegisterRequestDto;
import com.plaxa.pikabu.dto.UserReadDto;
import com.plaxa.pikabu.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserReadDto mapUserToUserReadDto(User user);

    User mapRegisterDtoToUser(RegisterRequestDto registerRequestDto);
}
