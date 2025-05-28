package com.cedacri.car_app.service;

import com.cedacri.car_app.dto.CarDto;
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

import static com.cedacri.car_app.utils.CarDTOUtils.getPreparedCarDto;
import static com.cedacri.car_app.utils.CarDTOUtils.getPreparedCarDtoElectric;
import static com.cedacri.car_app.utils.CarDTOUtils.getPreparedCarDtoKeiCar;
import static com.cedacri.car_app.utils.CarDTOUtils.getPreparedCarDtoList;
import static com.cedacri.car_app.utils.CarDTOUtils.getPreparedCarDtoRoadster;
import static com.cedacri.car_app.utils.CarDTOUtils.getPreparedCarDtoTruck;
import static com.cedacri.car_app.utils.CarDTOUtils.getPreparedCarDtoVan;
import static com.cedacri.car_app.utils.CarUtils.getPreparedCar;
import static com.cedacri.car_app.utils.CarUtils.getPreparedCarList;
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
    void testSaveCar_WhenItDoesNotExist_ReturnResponseCarDto() {
        Car savedCar = getPreparedCar();
        CarDto requestDto = getPreparedCarDto();

        when(carRepository.save(any(Car.class))).thenReturn(savedCar);

        CarDto responseDto = carService.saveCar(requestDto);

        assertEquals(requestDto, responseDto);

        verify(carRepository).save(any(Car.class));
    }

    @Test
    void testGetCarByVin_WhenItExists_ReturnMatchingCarDto() {
        Car car = getPreparedCar();
        CarDto expectedDto = getPreparedCarDto();

        when(carRepository.getById(any())).thenReturn(Optional.of(car));

        CarDto actualDto = carService.getCarByVin(expectedDto.vin());

        assertEquals(expectedDto.vin(), actualDto.vin());

        verify(carRepository).getById(expectedDto.vin());
    }

    @Test
    void testGetCarByVin_WhenItDoesNotExists_ThrowCarNotFoundException() {
        CarDto carDto = getPreparedCarDto();

        when(carRepository.getById(carDto.vin()))
                .thenReturn(Optional.empty());

        CarNotFoundException exception = assertThrows(CarNotFoundException.class,
                () -> carService.getCarByVin(carDto.vin()));

        assertEquals(String.format("Car VIN %s not found.", carDto.vin()), exception.getMessage());

        verify(carRepository).getById(carDto.vin());
    }

    @Test
    void testGetAllCars_ReturnCarList() {
        List<Car> cars = getPreparedCarList();
        List<CarDto> expectedDto = getPreparedCarDtoList();

        when(carRepository.getAll()).thenReturn(cars);

        List<CarDto> actualDto = carService.getAllCars();

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

        when(carRepository.getById(car.getVinCode())).thenReturn(Optional.of(car));

        assertEquals(1.3, carService.getVolumeInLiterByVin(car.getVinCode()));
    }

    @Test
    void testSetTypeByVolume_WhenVolumeIsEqualsToZero_SetElectricType() {
        CarDto requestDto = getPreparedCarDtoElectric();

        when(carRepository.save(any(Car.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CarDto responseDto = carService.saveCar(requestDto);

        assertEquals(FuelTypeEnum.ELECTRIC, responseDto.fuelType());
        assertEquals(TransmissionEnum.AUTOMATIC, responseDto.transmission());

        verify(carRepository).save(any(Car.class));
    }

    @Test
    void testSetTypeByVolume_WhenVolumeIsBiggerThan10000_SetTruckType() {
        CarDto requestDto = getPreparedCarDtoTruck();

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
        CarDto requestDto = getPreparedCarDtoKeiCar();

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
        CarDto requestDto = getPreparedCarDtoVan();

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
        CarDto requestDto = getPreparedCarDtoRoadster();

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
        CarDto requestDto = getPreparedCarDto();

        ArgumentCaptor<Car> captor = ArgumentCaptor.forClass(Car.class);

        when(carRepository.save(any(Car.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        carService.saveCar(requestDto);

        verify(carRepository).save(captor.capture());
        Car savedCar = captor.getValue();

        assertEquals(CarTypeEnum.COUPE, savedCar.getType());
    }
}
