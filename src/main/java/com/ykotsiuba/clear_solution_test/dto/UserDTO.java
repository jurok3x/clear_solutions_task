package com.ykotsiuba.clear_solution_test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;

    private String email;

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    private LocalDate birthDate;
}
