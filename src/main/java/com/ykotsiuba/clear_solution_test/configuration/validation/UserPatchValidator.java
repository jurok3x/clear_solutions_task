package com.ykotsiuba.clear_solution_test.configuration.validation;

import com.ykotsiuba.clear_solution_test.configuration.validation.annotations.ValidPatch;
import com.ykotsiuba.clear_solution_test.dto.PatchUserRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.ykotsiuba.clear_solution_test.utils.StringUtils.nullOrBlank;


public class UserPatchValidator implements ConstraintValidator<ValidPatch, PatchUserRequestDTO> {
    /**
     * Checks if at least one patch parameter is present.
     * @param requestDTO
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(PatchUserRequestDTO requestDTO, ConstraintValidatorContext constraintValidatorContext) {
        return !nullOrBlank(requestDTO.getFirstName()) ||
                !nullOrBlank(requestDTO.getLastName()) ||
                !nullOrBlank(requestDTO.getEmail()) ||
                !nullOrBlank(requestDTO.getPhone()) ||
                !nullOrBlank(requestDTO.getAddress()) ||
                requestDTO.getBirthDate() != null;
    }
}
