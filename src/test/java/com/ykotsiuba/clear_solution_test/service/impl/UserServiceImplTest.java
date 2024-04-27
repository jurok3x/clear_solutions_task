package com.ykotsiuba.clear_solution_test.service.impl;

import com.ykotsiuba.clear_solution_test.dto.DeleteUserResponseDTO;
import com.ykotsiuba.clear_solution_test.dto.PatchUserRequestDTO;
import com.ykotsiuba.clear_solution_test.dto.SaveUserRequestDTO;
import com.ykotsiuba.clear_solution_test.dto.UserDTO;
import com.ykotsiuba.clear_solution_test.entity.User;
import com.ykotsiuba.clear_solution_test.mapper.UserMapper;
import com.ykotsiuba.clear_solution_test.mapper.UserMapperImpl;
import com.ykotsiuba.clear_solution_test.repository.UserRepository;
import com.ykotsiuba.clear_solution_test.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ykotsiuba.clear_solution_test.utils.UserDataUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserService userService;

    private UserRepository userRepository;

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository, userMapper);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void whenSaveUser_thenReturnCorrectResponse() {
        User user = prepareUser();
        SaveUserRequestDTO requestDTO = prepareSaveUserRequest();
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO responseDTO = userService.save(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(requestDTO.getEmail(), responseDTO.getEmail());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void whenUpdateUser_thenReturnCorrectResponse() {
        User user = prepareUser();
        SaveUserRequestDTO requestDTO = prepareSaveUserRequest();
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO responseDTO = userService.update(requestDTO, user.getId().toString());

        assertNotNull(responseDTO);
        assertEquals(requestDTO.getEmail(), responseDTO.getEmail());

        verify(userRepository).save(any(User.class));
        verify(userRepository).findById(any(UUID.class));
    }

    @Test
    void whenPatchUser_thenReturnCorrectResponse() {
        User user = prepareUser();
        PatchUserRequestDTO requestDTO = preparePatchUserRequest();
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO responseDTO = userService.patch(requestDTO, user.getId().toString());

        assertNotNull(responseDTO);
        assertEquals(requestDTO.getEmail(), responseDTO.getEmail());

        verify(userRepository).save(any(User.class));
        verify(userRepository).findById(any(UUID.class));
    }

    @Test
    void whenDeleteUser_thenReturnCorrectResponse() {
        User user = prepareUser();
        doNothing().when(userRepository).delete(any(User.class));
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(user));

        DeleteUserResponseDTO responseDTO = userService.delete(user.getId().toString());

        assertNotNull(responseDTO);
        assertEquals("User deleted", responseDTO.getMessage());

        verify(userRepository).delete(any(User.class));
        verify(userRepository).findById(any(UUID.class));
    }

    @Test
    void whenFindByBirthDateRange_thenReturnCorrectResponse() {
        List<User> users = Arrays.asList(prepareUser());
        LocalDate from = LocalDate.now().minus(20, ChronoUnit.YEARS);
        LocalDate to = LocalDate.now().minus(18, ChronoUnit.YEARS);
        when(userRepository.findAllByBirthDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(users);

        List<UserDTO> responseDTO = userService.findByBirthDateRange(from, to);

        assertNotNull(responseDTO);
        assertFalse(responseDTO.isEmpty());

        verify(userRepository).findAllByBirthDateBetween(any(LocalDate.class), any(LocalDate.class));
    }
}