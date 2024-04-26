package com.ykotsiuba.clear_solution_test.dto;

import com.ykotsiuba.clear_solution_test.configuration.validation.annotations.ValidBirthDate;
import com.ykotsiuba.clear_solution_test.configuration.validation.annotations.ValidPatch;
import jakarta.validation.constraints.Email;
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
@ValidPatch
public class PatchUserRequestDTO {

    @Email(message = "email not valid")
    private String email;

    private String firstName;

    private String lastName;

    private String address;

    @Pattern(regexp = "\\+380\\d{9}", message = "phone not valid")
    private String phone;

    @ValidBirthDate
    private LocalDate birthDate;
}
