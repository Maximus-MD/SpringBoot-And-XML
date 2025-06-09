package com.cedacri.car_app.controller;

import com.cedacri.car_app.dto.CarRequestDto;
import com.cedacri.car_app.dto.CarResponseDto;
import com.cedacri.car_app.dto.ResponseDto;
import com.cedacri.car_app.services.CarService;
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

import java.util.List;

import static com.cedacri.car_app.utils.CarRequestDtoUtils.getPreparedCarRequestDto;
import static com.cedacri.car_app.utils.CarResponseDtoUtils.getPreparedCarResponseDto;
import static com.cedacri.car_app.utils.CarResponseDtoUtils.getPreparedCarResponseDtoList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    void shouldReturnOkStatusWithResponse_WhenGetCarIsSuccessful() throws Exception {
        CarRequestDto request = getPreparedCarRequestDto();
        CarResponseDto response = getPreparedCarResponseDto();

        when(carService.getCarByVin(request.vin())).thenReturn(response);

        mockMvc.perform(get("/cars/WDBUF56X48B123654"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.vin").value(response.vin()))
                .andExpect(jsonPath("$.name").value(response.name()))
                .andExpect(jsonPath("$.model").value(response.model()))
                .andExpect(jsonPath("$.manufactureYear").value(response.manufactureYear()))
                .andExpect(jsonPath("$.engineVolume").value(response.engineVolume()))
                .andExpect(jsonPath("$.enginePower").value(response.enginePower()))
                .andExpect(jsonPath("$.fuelType").value(response.fuelType().name()))
                .andExpect(jsonPath("$.transmission").value(response.transmission().name()))
                .andExpect(jsonPath("$.numOfSeats").value(response.numOfSeats()))
                .andExpect(jsonPath("$.doorsNum").value(response.doorsNum()))
                .andExpect(jsonPath("$.maxSpeed").value(response.maxSpeed()));
    }

    @Test
    void shouldReturnOkStatusWithResponse_WhenGetCarsIsSuccessful() throws Exception {
        List<CarResponseDto> cars = getPreparedCarResponseDtoList();

        when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/cars/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].vin").value(cars.get(0).vin()))
                .andExpect(jsonPath("$[0].name").value(cars.get(0).name()))
                .andExpect(jsonPath("$[0].model").value(cars.get(0).model()))
                .andExpect(jsonPath("$[0].manufactureYear").value(cars.get(0).manufactureYear()))
                .andExpect(jsonPath("$[0].engineVolume").value(cars.get(0).engineVolume()))
                .andExpect(jsonPath("$[0].enginePower").value(cars.get(0).enginePower()))
                .andExpect(jsonPath("$[0].fuelType").value(cars.get(0).fuelType().name()))
                .andExpect(jsonPath("$[0].transmission").value(cars.get(0).transmission().name()))
                .andExpect(jsonPath("$[0].numOfSeats").value(cars.get(0).numOfSeats()))
                .andExpect(jsonPath("$[0].doorsNum").value(cars.get(0).doorsNum()))
                .andExpect(jsonPath("$[0].maxSpeed").value(cars.get(0).maxSpeed()))

                .andExpect(jsonPath("$[0].vin").value(cars.get(0).vin()))
                .andExpect(jsonPath("$[0].name").value(cars.get(0).name()))
                .andExpect(jsonPath("$[0].model").value(cars.get(0).model()))
                .andExpect(jsonPath("$[0].manufactureYear").value(cars.get(0).manufactureYear()))
                .andExpect(jsonPath("$[0].engineVolume").value(cars.get(0).engineVolume()))
                .andExpect(jsonPath("$[0].enginePower").value(cars.get(0).enginePower()))
                .andExpect(jsonPath("$[0].fuelType").value(cars.get(0).fuelType().name()))
                .andExpect(jsonPath("$[0].transmission").value(cars.get(0).transmission().name()))
                .andExpect(jsonPath("$[0].numOfSeats").value(cars.get(0).numOfSeats()))
                .andExpect(jsonPath("$[0].doorsNum").value(cars.get(0).doorsNum()))
                .andExpect(jsonPath("$[0].maxSpeed").value(cars.get(0).maxSpeed()));
    }

    @Test
    void shouldReturnOkStatusWithResponse_WhenSaveIsSuccessful() throws Exception {
        CarRequestDto requestDto = getPreparedCarRequestDto();
        CarResponseDto responseDto = getPreparedCarResponseDto();

        when(carService.saveCar(requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/cars/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.vin").value(responseDto.vin()))
                .andExpect(jsonPath("$.name").value(responseDto.name()))
                .andExpect(jsonPath("$.model").value(responseDto.model()))
                .andExpect(jsonPath("$.manufactureYear").value(responseDto.manufactureYear()))
                .andExpect(jsonPath("$.engineVolume").value(responseDto.engineVolume()))
                .andExpect(jsonPath("$.enginePower").value(responseDto.enginePower()))
                .andExpect(jsonPath("$.fuelType").value(responseDto.fuelType().name()))
                .andExpect(jsonPath("$.transmission").value(responseDto.transmission().name()))
                .andExpect(jsonPath("$.numOfSeats").value(responseDto.numOfSeats()))
                .andExpect(jsonPath("$.doorsNum").value(responseDto.doorsNum()))
                .andExpect(jsonPath("$.maxSpeed").value(responseDto.maxSpeed()));
    }

    @Test
    void shouldReturnOkStatusWithResponse_WhenCarDeleteIsSuccessful() throws Exception {
        CarRequestDto requestDto = getPreparedCarRequestDto();
        ResponseDto responseDto = new ResponseDto(true);

        when(carService.deleteCarByVin(any())).thenReturn(responseDto);

        mockMvc.perform(delete("/cars/delete/WAUZZZ8K9BA12796")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(responseDto.success()));
    }

    @Test
    void shouldReturnOkStatusWithResponse_WhenConvertToLitersIsSuccessful() throws Exception {
        String vinCode = "WDBUF56X48B123654";
        String message = "Volume of car Mazda RX-7 is 1.3L.";

        when(carService.getVolumeInLiterByVin(any())).thenReturn(message);

        mockMvc.perform(get("/cars/get-volume/{vinCode}", vinCode)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isOk())
                .andExpect(content().string(message));
    }
}
