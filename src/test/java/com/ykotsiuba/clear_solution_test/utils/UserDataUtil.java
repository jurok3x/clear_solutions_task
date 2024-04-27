package com.ykotsiuba.clear_solution_test.utils;

import com.ykotsiuba.clear_solution_test.dto.PatchUserRequestDTO;
import com.ykotsiuba.clear_solution_test.dto.SaveUserRequestDTO;
import com.ykotsiuba.clear_solution_test.entity.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class UserDataUtil {

    public static User prepareUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .email("johndoe@example.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.now().minus(22, ChronoUnit.YEARS))
                .phone("+380950000000")
                .address("456 Elm St")
                .build();
    }

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
