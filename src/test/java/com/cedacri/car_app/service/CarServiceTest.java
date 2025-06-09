package com.cedacri.car_app.service;

import com.cedacri.car_app.dto.CarRequestDto;
import com.cedacri.car_app.dto.CarResponseDto;
import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.entities.enums.CarTypeEnum;
import com.cedacri.car_app.entities.enums.FuelTypeEnum;
import com.cedacri.car_app.entities.enums.TransmissionEnum;
import com.cedacri.car_app.exceptions.CarNotFoundException;
import com.cedacri.car_app.repositories.CarRepository;
import com.cedacri.car_app.services.impl.CarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.cedacri.car_app.utils.CarRequestDtoUtils.getPreparedCarRequestDto;
import static com.cedacri.car_app.utils.CarRequestDtoUtils.getPreparedCarRequestDtoElectric;
import static com.cedacri.car_app.utils.CarRequestDtoUtils.getPreparedCarRequestDtoKeiCar;
import static com.cedacri.car_app.utils.CarRequestDtoUtils.getPreparedCarRequestDtoRoadster;
import static com.cedacri.car_app.utils.CarRequestDtoUtils.getPreparedCarRequestDtoTruck;
import static com.cedacri.car_app.utils.CarRequestDtoUtils.getPreparedCarRequestDtoUniversalCar;
import static com.cedacri.car_app.utils.CarRequestDtoUtils.getPreparedCarRequestDtoVan;
import static com.cedacri.car_app.utils.CarResponseDtoUtils.getPreparedCarResponseDtoList;
import static com.cedacri.car_app.utils.CarUtils.getPreparedCar;
import static com.cedacri.car_app.utils.CarUtils.getPreparedCarList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void testSaveCar_WhenItDoesNotExist_ReturnCarResponseDto() {
        Car savedCar = getPreparedCar();
        CarRequestDto requestDto = getPreparedCarRequestDto();

        when(carRepository.save(any(Car.class))).thenReturn(savedCar);

        CarResponseDto responseDto = carService.saveCar(requestDto);

        assertAll(
                () -> assertEquals(requestDto.vin(), responseDto.vin()),
                () -> assertEquals(requestDto.name(), responseDto.name()),
                () -> assertEquals(requestDto.model(), responseDto.model()),
                () -> assertEquals(requestDto.manufactureYear(), responseDto.manufactureYear()),
                () -> assertEquals(requestDto.engineVolume(), responseDto.engineVolume()),
                () -> assertEquals(requestDto.enginePower(), responseDto.enginePower()),
                () -> assertEquals(requestDto.fuelType(), responseDto.fuelType()),
                () -> assertEquals(requestDto.transmission(), responseDto.transmission()),
                () -> assertEquals(requestDto.numOfSeats(), responseDto.numOfSeats()),
                () -> assertEquals(requestDto.doorsNum(), responseDto.doorsNum()),
                () -> assertEquals(requestDto.maxSpeed(), responseDto.maxSpeed())
        );

        verify(carRepository).save(any(Car.class));
    }

    @Test
    void testGetCarByVin_WhenItExists_ReturnMatchingCarDto() {
        Car car = getPreparedCar();
        CarRequestDto expectedDto = getPreparedCarRequestDto();

        when(carRepository.getById(any())).thenReturn(Optional.of(car));

        CarResponseDto actualDto = carService.getCarByVin(expectedDto.vin());

        assertEquals(expectedDto.vin(), actualDto.vin());

        verify(carRepository).getById(expectedDto.vin());
    }

    @Test
    void testGetCarByVin_WhenItDoesNotExists_ThrowCarNotFoundException() {
        CarRequestDto carRequestDto = getPreparedCarRequestDto();

        when(carRepository.getById(carRequestDto.vin()))
                .thenReturn(Optional.empty());

        CarNotFoundException exception = assertThrows(CarNotFoundException.class,
                () -> carService.getCarByVin(carRequestDto.vin()));

        assertEquals(String.format("Car VIN %s not found.", carRequestDto.vin()), exception.getMessage());

        verify(carRepository).getById(carRequestDto.vin());
    }

    @Test
    void testGetAllCars_ReturnCarList() {
        List<Car> cars = getPreparedCarList();
        List<CarResponseDto> expectedDto = getPreparedCarResponseDtoList();

        when(carRepository.getAll()).thenReturn(cars);

        List<CarResponseDto> actualDto = carService.getAllCars();

        assertEquals(expectedDto, actualDto);

        verify(carRepository).getAll();
    }

    @Test
    void testDeleteCarByVin_WhenItExists_ReturnResponseStatus() {
        Car car = getPreparedCar();

        when(carRepository.getById(car.getVinCode()))
                .thenReturn(Optional.of(car));

        carService.deleteCarByVin(car.getVinCode());

        verify(carRepository).removeById(car.getVinCode());
    }

    @Test
    void testGetVolumeInLiters_WhenCarExists_ReturnLiters() {
        Car car = getPreparedCar();
        String message = "Volume of car Mazda RX-7 is 1.3L.";

        when(carRepository.getById(car.getVinCode())).thenReturn(Optional.of(car));

        assertEquals(message, carService.getVolumeInLiterByVin(car.getVinCode()));
    }

    @Test
    void testSetTypeByVolume_WhenVolumeIsEqualsToZero_SetElectricType() {
        CarRequestDto requestDto = getPreparedCarRequestDtoElectric();

        when(carRepository.save(any(Car.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CarResponseDto responseDto = carService.saveCar(requestDto);

        assertEquals(FuelTypeEnum.ELECTRIC, responseDto.fuelType());
        assertEquals(TransmissionEnum.AUTOMATIC, responseDto.transmission());

        verify(carRepository).save(any(Car.class));
    }

    @Test
    void testSetTypeByVolume_WhenVolumeIsBiggerThan10000_SetTruckType() {
        CarRequestDto requestDto = getPreparedCarRequestDtoTruck();

        ArgumentCaptor<Car> captor = ArgumentCaptor.forClass(Car.class);

        when(carRepository.save(any(Car.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        carService.saveCar(requestDto);

        verify(carRepository).save(captor.capture());
        Car savedCar = captor.getValue();

        assertEquals(CarTypeEnum.TRUCK, savedCar.getType());
    }

    @Test
    void testSetTypeByVolume_WhenVolumeIsFrom600To900_SetKeiCarType() {
        CarRequestDto requestDto = getPreparedCarRequestDtoKeiCar();

        ArgumentCaptor<Car> captor = ArgumentCaptor.forClass(Car.class);

        when(carRepository.save(any(Car.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        carService.saveCar(requestDto);

        verify(carRepository).save(captor.capture());
        Car savedCar = captor.getValue();

        assertEquals(CarTypeEnum.KEI_CAR, savedCar.getType());
    }

    @Test
    void testSetTypeByNumberOfSeats_WhenNumberIsSeven_SetVanType() {
        CarRequestDto requestDto = getPreparedCarRequestDtoVan();

        ArgumentCaptor<Car> captor = ArgumentCaptor.forClass(Car.class);

        when(carRepository.save(any(Car.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        carService.saveCar(requestDto);

        verify(carRepository).save(captor.capture());
        Car savedCar = captor.getValue();

        assertEquals(CarTypeEnum.VAN, savedCar.getType());
    }

    @Test
    void testSetTypeByNumberOfSeats_WhenNumberOfSeatsAndDoorsAreTwo_SetRoadsterType() {
        CarRequestDto requestDto = getPreparedCarRequestDtoRoadster();

        ArgumentCaptor<Car> captor = ArgumentCaptor.forClass(Car.class);

        when(carRepository.save(any(Car.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        carService.saveCar(requestDto);

        verify(carRepository).save(captor.capture());
        Car savedCar = captor.getValue();

        assertEquals(CarTypeEnum.ROADSTER, savedCar.getType());
    }

    @Test
    void testSetTypeByNumberOfSeats_WhenNumberOfSeatsIsFourAndDoorsAreTwo_SetCoupeType() {
        CarRequestDto requestDto = getPreparedCarRequestDto();

        ArgumentCaptor<Car> captor = ArgumentCaptor.forClass(Car.class);

        when(carRepository.save(any(Car.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        carService.saveCar(requestDto);

        verify(carRepository).save(captor.capture());
        Car savedCar = captor.getValue();

        assertEquals(CarTypeEnum.COUPE, savedCar.getType());
    }

    @Test
    void testSetTypeByNumberOfSeats_WhenNumberOfSeatsIsFiveAndDoorsAreFive_SetUniversalType() {
        CarRequestDto requestDto = getPreparedCarRequestDtoUniversalCar();

        ArgumentCaptor<Car> captor = ArgumentCaptor.forClass(Car.class);

        when(carRepository.save(any(Car.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        carService.saveCar(requestDto);

        verify(carRepository).save(captor.capture());
        Car savedCar = captor.getValue();

        assertEquals(CarTypeEnum.UNIVERSAL, savedCar.getType());
    }
}
