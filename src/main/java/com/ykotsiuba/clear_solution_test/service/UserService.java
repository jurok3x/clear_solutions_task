package com.ykotsiuba.clear_solution_test.service;

import com.ykotsiuba.clear_solution_test.dto.DeleteUserResponseDTO;
import com.ykotsiuba.clear_solution_test.dto.PatchUserRequestDTO;
import com.ykotsiuba.clear_solution_test.dto.SaveUserRequestDTO;
import com.ykotsiuba.clear_solution_test.dto.UserDTO;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    /**
     * Saves a new user based on the provided data.
     *
     * @param requestDTO The data representing the user to be saved.
     * @return The DTO (Data Transfer Object) representing the saved user.
     */
    UserDTO save(SaveUserRequestDTO requestDTO);

    /**
     * Updates an existing user with the provided data.
     *
     * @param requestDTO The data representing the updates to be applied to the user.
     * @param id         The unique identifier of the user to be updated.
     * @return The DTO representing the updated user.
     * @throws jakarta.persistence.EntityNotFoundException when user not found.
     */
    UserDTO update(SaveUserRequestDTO requestDTO, String id);

    /**
     * Applies partial updates to an existing user.
     *
     * @param requestDTO The data representing the partial updates to be applied.
     * @param id         The unique identifier of the user to be patched.
     * @return The DTO representing the patched user.
     * @throws jakarta.persistence.EntityNotFoundException when user not found.
     */
    UserDTO patch(PatchUserRequestDTO requestDTO, String id);


    /**
     * Deletes a user by its unique identifier.
     *
     * @param id The unique identifier of the user to be deleted.
     * @return The response DTO indicating the success of the deletion operation.
     * @throws jakarta.persistence.EntityNotFoundException when user not found.
     */
    DeleteUserResponseDTO delete(String id);

    /**
     * Finds users within a specified date of birth range.
     *
     * @param from The start date of the date of birth range.
     * @param to   The end date of the date of birth range.
     * @return A list of DTOs representing users whose date of birth fall within the specified range.
     * @throws IllegalArgumentException when date range is incorrect.
     */
    List<UserDTO> findByBirthDateRange(LocalDate from, LocalDate to);
}
