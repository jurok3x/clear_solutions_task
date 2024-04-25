package com.ykotsiuba.clear_solution_test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class SaveUserRequestDTO {

    @NotBlank(message = "email required")
    @Email@NotBlank(message = "email not valid")
    private String email;

    @NotBlank(message = "first name required")
    private String firstName;

    @NotBlank(message = "last name required")
    private String lastName;

    private String address;

    private String phone;

    private LocalDate birthDate;
}
