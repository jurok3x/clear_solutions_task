package com.ykotsiuba.clear_solution_test.utils;

import com.ykotsiuba.clear_solution_test.dto.PatchUserRequestDTO;
import com.ykotsiuba.clear_solution_test.dto.SaveUserRequestDTO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class UserDataUtil {

    public static SaveUserRequestDTO prepareSaveUserRequest() {
        return SaveUserRequestDTO.builder()
                .email("johndoe@example.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.now().minus(22, ChronoUnit.YEARS))
                .build();
    }

    public static PatchUserRequestDTO preparePatchUserRequest() {
        return PatchUserRequestDTO.builder()
                .email("johndoe@example.com")
                .build();
    }
}
