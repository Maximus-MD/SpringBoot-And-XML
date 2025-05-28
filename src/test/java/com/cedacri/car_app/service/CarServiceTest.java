package com.cedacri.car_app.service;

import com.cedacri.car_app.dto.CarDto;
import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.entities.enums.CarTypeEnum;
import com.cedacri.car_app.repositories.CarRepository;
import com.cedacri.car_app.services.impl.CarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;

import static com.cedacri.car_app.utils.CarDTOUtils.getPreparedCarDto;
import static com.cedacri.car_app.utils.CarUtils.getPreparedCar;
import static com.cedacri.car_app.utils.CarUtils.getPreparedTruck;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void testSaveCar_WhenItDoesNotExist(){
        Car savedCar = getPreparedCar();
        CarDto requestDto = getPreparedCarDto();

        when(carRepository.save(any(Car.class))).thenReturn(savedCar);

        CarDto responseDto = carService.saveCar(requestDto);

        assertNotNull(responseDto);
        assertEquals(requestDto, responseDto);

        verify(carRepository).save(any(Car.class));
    }

    @Test
    void testSetTypeByVolume_WhenVolumeIsBiggerThanTenThousand() throws Exception {
        Car car = getPreparedTruck();

        CarTypeEnum expected = CarTypeEnum.TRUCK;

        Method method = carService.getClass().getDeclaredMethod("setTypeByVolume", Car.class);
        method.setAccessible(true);

        method.invoke(carService, car);

        assertEquals(expected, car.getType());
    }
}
