package com.ykotsiuba.clear_solution_test.mapper;

import com.ykotsiuba.clear_solution_test.dto.SaveUserRequestDTO;
import com.ykotsiuba.clear_solution_test.dto.UserDTO;
import com.ykotsiuba.clear_solution_test.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO fromEntityToDTO(User user);

    @Mapping(target = "id", ignore = true)
    User fromSaveRequestToEntity(SaveUserRequestDTO requestDTO);

    User fromDTOToEntity(UserDTO userDTO);

}
