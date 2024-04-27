package com.ykotsiuba.clear_solution_test.controller;

import com.ykotsiuba.clear_solution_test.dto.DeleteUserResponseDTO;
import com.ykotsiuba.clear_solution_test.dto.PatchUserRequestDTO;
import com.ykotsiuba.clear_solution_test.dto.SaveUserRequestDTO;
import com.ykotsiuba.clear_solution_test.dto.UserDTO;
import com.ykotsiuba.clear_solution_test.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody @Valid SaveUserRequestDTO requestDTO) {
        UserDTO responseDTO = userService.save(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody @Valid SaveUserRequestDTO requestDTO,
                                          @PathVariable String id) {
        UserDTO responseDTO = userService.update(requestDTO, id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> patch(@RequestBody @Valid PatchUserRequestDTO requestDTO,
                                          @PathVariable String id) {
        UserDTO responseDTO = userService.patch(requestDTO, id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteUserResponseDTO> delete(@PathVariable String id) {
        DeleteUserResponseDTO responseDTO = userService.delete(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findBetweenBirthDays(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        List<UserDTO> responseDTO = userService.findByBirthDateRange(from, to);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
