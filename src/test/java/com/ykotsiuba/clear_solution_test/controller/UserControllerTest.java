package com.ykotsiuba.clear_solution_test.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ykotsiuba.clear_solution_test.dto.*;
import com.ykotsiuba.clear_solution_test.entity.User;
import com.ykotsiuba.clear_solution_test.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ykotsiuba.clear_solution_test.utils.UserDataUtil.preparePatchUserRequest;
import static com.ykotsiuba.clear_solution_test.utils.UserDataUtil.prepareSaveUserRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private static final String API_URL = "/api/v1/users";

    private static final String ID ="00000000-0000-0000-0000-000000000001";

    private static final String ID_URL = API_URL + "/%s";

    private static final ObjectMapper DEFAULT_MAPPER;

    static {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        DEFAULT_MAPPER = mapper;
    }

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Value("${minimum.age}")
    private Integer minimumAge;

    @Test
    @Transactional
    public void testSaveUser() throws Exception {
        SaveUserRequestDTO requestDTO = prepareSaveUserRequest();
        String body = DEFAULT_MAPPER.writeValueAsString(requestDTO);

        MvcResult mvcResult = mvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        UserDTO responseDTO = DEFAULT_MAPPER.readValue(response, UserDTO.class);
        UUID id = responseDTO.getId();
        assertNotNull(id);

        Optional<User> byId = userRepository.findById(id);
        assertFalse(byId.isEmpty());
    }

    @Test
    @Transactional
    public void testSaveUser_invalidEmail() throws Exception {
        SaveUserRequestDTO requestDTO = prepareSaveUserRequest();
        requestDTO.setEmail("wrong_email");
        String body = DEFAULT_MAPPER.writeValueAsString(requestDTO);

        MvcResult mvcResult = mvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIException responseDTO = DEFAULT_MAPPER.readValue(response, APIException.class);
        String error = responseDTO.getErrors().get(0);
        assertEquals("email not valid", error);
    }

    @Test
    @Transactional
    public void testSaveArticle_invalidDateOfBirth() throws Exception {
        SaveUserRequestDTO requestDTO = prepareSaveUserRequest();
        requestDTO.setBirthDate(LocalDate.now().minus(15, ChronoUnit.YEARS));
        String body = DEFAULT_MAPPER.writeValueAsString(requestDTO);

        MvcResult mvcResult = mvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIException responseDTO = DEFAULT_MAPPER.readValue(response, APIException.class);
        String error = responseDTO.getErrors().get(0);
        assertEquals(String.format("User should have minimum %d years", minimumAge), error);
    }

    @Test
    public void testGetUsers() throws Exception {
        MvcResult mvcResult = mvc.perform(get(API_URL)
                        .queryParam("from", "1990-01-01")
                        .queryParam("to", "1995-12-31")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        List<UserDTO> responseDTO = DEFAULT_MAPPER.readValue(response, new TypeReference<List<UserDTO>>() {});
        assertEquals(2, responseDTO.size());
    }

    @Test
    public void testGetUsers_invalidDateRange() throws Exception {
        MvcResult mvcResult = mvc.perform(get(API_URL)
                        .queryParam("from", "1995-01-01")
                        .queryParam("to", "1990-12-31")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIException responseDTO = DEFAULT_MAPPER.readValue(response, APIException.class);
        String error = responseDTO.getErrors().get(0);
        assertEquals("Invalid date range", error);
    }

    @Test
    @Transactional
    public void testUpdateUser() throws Exception {
        SaveUserRequestDTO requestDTO = prepareSaveUserRequest();
        requestDTO.setEmail("johnsnow@example.com");
        String body = DEFAULT_MAPPER.writeValueAsString(requestDTO);
        MvcResult mvcResult = mvc.perform(put(String.format(ID_URL, ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        UserDTO responseDTO = DEFAULT_MAPPER.readValue(response, UserDTO.class);;
        assertEquals(requestDTO.getEmail(), responseDTO.getEmail());
    }

    @Test
    @Transactional
    public void testUpdateUser_invalidId() throws Exception {
        SaveUserRequestDTO requestDTO = prepareSaveUserRequest();
        String body = DEFAULT_MAPPER.writeValueAsString(requestDTO);
        String invalidId = "00000000-0000-0000-0000-000000000020";
        MvcResult mvcResult = mvc.perform(put(String.format(ID_URL, invalidId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIException responseDTO = DEFAULT_MAPPER.readValue(response, APIException.class);
        String error = responseDTO.getErrors().get(0);
        assertEquals(String.format("User with id %s not found", invalidId), error);
    }

    @Test
    @Transactional
    public void testDeleteUser() throws Exception {
        MvcResult mvcResult = mvc.perform(delete(String.format(ID_URL, ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        DeleteUserResponseDTO responseDTO = DEFAULT_MAPPER.readValue(response, DeleteUserResponseDTO.class);
        assertEquals("User deleted", responseDTO.getMessage());
    }

    @Test
    @Transactional
    public void testPatchUser() throws Exception {
        PatchUserRequestDTO requestDTO = preparePatchUserRequest();
        String body = DEFAULT_MAPPER.writeValueAsString(requestDTO);
        MvcResult mvcResult = mvc.perform(patch(String.format(ID_URL, ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        UserDTO responseDTO = DEFAULT_MAPPER.readValue(response, UserDTO.class);;
        assertEquals(requestDTO.getEmail(), responseDTO.getEmail());
    }

    @Test
    @Transactional
    public void testPatchUser_invalidParams() throws Exception {
        PatchUserRequestDTO requestDTO = preparePatchUserRequest();
        requestDTO.setEmail(null);
        String body = DEFAULT_MAPPER.writeValueAsString(requestDTO);
        MvcResult mvcResult = mvc.perform(patch(String.format(ID_URL, ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIException responseDTO = DEFAULT_MAPPER.readValue(response, APIException.class);
        String error = responseDTO.getErrors().get(0);
        assertEquals("at least one parameter should be present", error);
    }
}