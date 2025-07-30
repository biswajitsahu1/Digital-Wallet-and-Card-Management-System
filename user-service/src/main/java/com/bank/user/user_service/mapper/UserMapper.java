package com.bank.user.user_service.mapper;

import com.bank.user.user_service.dto.UserRegisterRequest;
import com.bank.user.user_service.dto.UserRegisterResponse;
import com.bank.user.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target= "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", constant = "user")

    /**
     * Maps a {@link UserRegisterRequest} DTO to a {@link User} entity.
     *
     * @param request the user register request DTO
     * @return the corresponding user entity
     */
    User toEntity(UserRegisterRequest request);

    /**
     * Maps a {@link User} entity to a {@link UserRegisterRequest} DTO.
     *
     * @param user the user entity to be mapped
     * @return the corresponding user register request DTO
     */
    UserRegisterResponse toDto(User user);


}
