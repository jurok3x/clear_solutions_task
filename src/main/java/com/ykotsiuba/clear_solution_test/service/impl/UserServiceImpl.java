package com.ykotsiuba.clear_solution_test.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ykotsiuba.clear_solution_test.dto.DeleteUserResponseDTO;
import com.ykotsiuba.clear_solution_test.dto.PatchUserRequestDTO;
import com.ykotsiuba.clear_solution_test.dto.SaveUserRequestDTO;
import com.ykotsiuba.clear_solution_test.dto.UserDTO;
import com.ykotsiuba.clear_solution_test.entity.User;
import com.ykotsiuba.clear_solution_test.mapper.UserMapper;
import com.ykotsiuba.clear_solution_test.repository.UserRepository;
import com.ykotsiuba.clear_solution_test.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final ObjectMapper DEFAULT_MAPPER;

    static {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        DEFAULT_MAPPER = mapper;
    }

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDTO save(SaveUserRequestDTO requestDTO) {
        User user = userMapper.fromSaveRequestToEntity(requestDTO);
        User savedUser = userRepository.save(user);
        return userMapper.fromEntityToDTO(savedUser);
    }

    @Override
    public UserDTO update(SaveUserRequestDTO requestDTO, String id) {
        User userById = findOrThrow(id);
        User user = userMapper.fromSaveRequestToEntity(requestDTO);
        user.setId(userById.getId());
        User updatedUser = userRepository.save(user);
        return userMapper.fromEntityToDTO(updatedUser);
    }

    private User findOrThrow(String id) {
        Optional<User> optionalUser = userRepository.findById(UUID.fromString(id));
        return optionalUser.orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id %s not found", id)));
    }

    @Override
    public UserDTO patch(PatchUserRequestDTO requestDTO, String id) {
        User user = findOrThrow(id);
        User patchedUser = applyPatchToUser(requestDTO, user);
        return userMapper.fromEntityToDTO(patchedUser);
    }

    private User applyPatchToUser(PatchUserRequestDTO requestDTO, User oldUser) {
        try {
            String patchJson = DEFAULT_MAPPER.writeValueAsString(requestDTO);
            String userJson = DEFAULT_MAPPER.writeValueAsString(oldUser);

            JsonNode patchNode = DEFAULT_MAPPER.readTree(patchJson);
            JsonNode userNode = DEFAULT_MAPPER.readTree(userJson);

            JsonNode patchedNode = applyPatch(userNode, patchNode);
            return DEFAULT_MAPPER.treeToValue(patchedNode, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonNode applyPatch(JsonNode originalNode, JsonNode patchNode) {
        JsonNode patchedNode = originalNode.deepCopy();

        patchNode.fields().forEachRemaining(entry -> {
            String fieldName = entry.getKey();
            JsonNode patchValue = entry.getValue();
            ((ObjectNode) patchedNode).replace(fieldName, patchValue);
        });

        return patchedNode;
    }

    @Override
    public DeleteUserResponseDTO delete(String id) {
        User userById = findOrThrow(id);
        userRepository.delete(userById);
        return DeleteUserResponseDTO.builder()
                .message("User deleted")
                .build();
    }

    @Override
    public List<UserDTO> findByBirthDateRange(LocalDate from, LocalDate to) {
        validateDates(from, to);
        List<User> users = userRepository.findAllByBirthDateBetween(from, to);
        return users.stream()
                .map(userMapper::fromEntityToDTO)
                .toList();
    }

    private void validateDates(LocalDate from, LocalDate to) {
        if(to.isBefore(from)) {
            throw new IllegalArgumentException("Invalid date range");
        }
    }
}
