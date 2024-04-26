package com.ykotsiuba.clear_solution_test.service;

import com.ykotsiuba.clear_solution_test.dto.DeleteUserResponseDTO;
import com.ykotsiuba.clear_solution_test.dto.PatchUserRequestDTO;
import com.ykotsiuba.clear_solution_test.dto.SaveUserRequestDTO;
import com.ykotsiuba.clear_solution_test.dto.UserDTO;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    UserDTO save(SaveUserRequestDTO requestDTO);

    UserDTO update(SaveUserRequestDTO requestDTO, String id);

    UserDTO patch(PatchUserRequestDTO requestDTO, String id);

    DeleteUserResponseDTO delete(String id);

    List<UserDTO> findByBirthDateRange(LocalDate from, LocalDate to);
}
