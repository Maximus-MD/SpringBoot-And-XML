package com.cedacri.car_app.controller;

import com.cedacri.car_app.dto.OwnerDto;
import com.cedacri.car_app.dto.ResponseDto;
import com.cedacri.car_app.services.OwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.cedacri.car_app.utils.OwnerDTOUtils.getPreparedOwnerDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OwnerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerController ownerController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void shouldReturnOkStatusWithResponse_WhenGetOwnerIsSuccessful() throws Exception{
        OwnerDto ownerDto = getPreparedOwnerDto();

        when(ownerService.getOwnerById("d3602875-c872-4892-a7e5-c9564fae1a9b")).thenReturn(ownerDto);

        mockMvc.perform(get("/owner/d3602875-c872-4892-a7e5-c9564fae1a9b"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(ownerDto.firstName()))
                .andExpect(jsonPath("$.lastName").value(ownerDto.lastName()));
    }

    @Test
    void shouldReturnOkStatusWithResponse_WhenOwnerSaveIsSuccessful() throws Exception {
        OwnerDto requestDto = getPreparedOwnerDto();
        OwnerDto responseDto = getPreparedOwnerDto();

        when(ownerService.saveOwner(any())).thenReturn(responseDto);

        mockMvc.perform(post("/owner/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(responseDto.firstName()))
                .andExpect(jsonPath("$.lastName").value(responseDto.lastName()));
    }

    @Test
    void shouldReturnOkStatusWithResponse_WhenOwnerDeleteIsSuccessful() throws Exception{
        OwnerDto ownerDto = getPreparedOwnerDto();
        ResponseDto responseDto = new ResponseDto(true);

        when(ownerService.deleteOwnerByUuid(any())).thenReturn(responseDto);

        mockMvc.perform(delete("/owner/delete/3bd6d2a5-b149-4fbb-89f8-01c37ef25616")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(responseDto.success()));
    }

    @Test
    void shouldReturnOkStatusWithResponse_WhenGetAllOwners() throws Exception{
        when(ownerService.getAllOwners()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/owner/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void shouldReturnOkStatusWithResponse_WhenOwnerAddToCarIsSuccessful() throws Exception{
        String vin = "1HGCM82633A008951";
        String userId = "0900e854-6eb8-47ab-9eca-8bd81f9a7264";
        ResponseDto responseDto = new ResponseDto(true);

        when(ownerService.addOwnerToCar("1HGCM82633A008951","0900e854-6eb8-47ab-9eca-8bd81f9a7264")).thenReturn(responseDto);

        mockMvc.perform(post("/owner/add-owner")
                        .param("vin", vin)
                        .param("userId", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(responseDto.success()));
    }
}
