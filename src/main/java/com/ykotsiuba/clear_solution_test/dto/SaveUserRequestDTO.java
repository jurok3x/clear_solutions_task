package com.ykotsiuba.clear_solution_test.dto;

import com.ykotsiuba.clear_solution_test.configuration.validation.annotations.ValidBirthDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserRequestDTO {

    @NotBlank(message = "email required")
    @Email(message = "email not valid")
    private String email;

    @NotBlank(message = "first name required")
    private String firstName;

    @NotBlank(message = "last name required")
    private String lastName;

    private String address;

    @Pattern(regexp = "\\+380\\d{9}", message = "phone not valid")
    private String phone;

    @NotNull(message = "birth date required")
    @ValidBirthDate
    private LocalDate birthDate;
}
