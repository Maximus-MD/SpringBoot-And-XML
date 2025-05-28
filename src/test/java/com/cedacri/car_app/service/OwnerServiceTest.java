package com.cedacri.car_app.service;

import com.cedacri.car_app.dto.OwnerDto;
import com.cedacri.car_app.dto.ResponseDto;
import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.entities.Owner;
import com.cedacri.car_app.exceptions.OwnerNotFoundException;
import com.cedacri.car_app.repositories.CarRepository;
import com.cedacri.car_app.repositories.OwnerRepository;
import com.cedacri.car_app.services.impl.OwnerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.cedacri.car_app.utils.CarUtils.getPreparedCar;
import static com.cedacri.car_app.utils.OwnerDTOUtils.getPreparedOwnerDto;
import static com.cedacri.car_app.utils.OwnerUtils.getPreparedOwner;
import static com.cedacri.car_app.utils.OwnerUtils.getPreparedOwnersList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @Test
    void testGetOwnerById_WhenItExists() {
        Owner owner = getPreparedOwner();

        when(ownerRepository.getById(owner.getUuid())).thenReturn(Optional.of(owner));

        OwnerDto result = ownerService.getOwnerById(owner.getUuid());

        assertEquals(owner.getFirstName(), result.firstName());
        assertEquals(owner.getLastName(), result.lastName());

        verify(ownerRepository).getById(owner.getUuid());
    }

    @Test
    void testGetOwnerById_WhenItDoesNotExist_ThrowOwnerNotFoundException() {
        Owner owner = getPreparedOwner();

        when(ownerRepository.getById(any())).thenReturn(Optional.empty());

        OwnerNotFoundException exception = assertThrows(OwnerNotFoundException.class,
                () -> ownerService.getOwnerById(owner.getUuid()));

        assertEquals(String.format("Owner with uuid %s not found.", owner.getUuid()), exception.getMessage());

        verify(ownerRepository).getById(owner.getUuid());
    }

    @Test
    void testSaveOwner_WhetItDoesNotExist_ReturnResponeDto() {
        Owner owner = getPreparedOwner();
        OwnerDto request = getPreparedOwnerDto();

        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        OwnerDto response = ownerService.saveOwner(request);

        assertEquals(request, response);

        verify(ownerRepository).save(any(Owner.class));
    }

    @Test
    void testAddOwnerToCar_ReturnWithSuccessResponse() {
        Owner owner = getPreparedOwner();
        Car car = getPreparedCar();

        when(ownerRepository.getById(owner.getUuid())).thenReturn(Optional.of(owner));
        when(carRepository.getById(car.getVinCode())).thenReturn(Optional.of(car));

        ResponseDto response = ownerService.addOwnerToCar(car.getVinCode(), owner.getUuid());
        assertTrue(response.success());

        verify(carRepository).save(any(Car.class));
    }

    @Test
    void testDeleteOwner_WhenItExists() {
        Owner owner = getPreparedOwner();

        when(ownerRepository.getById(owner.getUuid()))
                .thenReturn(Optional.of(owner));

        ownerService.deleteOwnerByUuid(owner.getUuid());

        verify(ownerRepository).removeById(owner.getUuid());
    }

    @Test
    void testGetAllOwners_ReturnOwnerList() {
        List<Owner> owners = getPreparedOwnersList();

        when(ownerRepository.getAll()).thenReturn(owners);

        List<OwnerDto> result = ownerService.getAllOwners();

        assertNotNull(result);
        assertEquals(owners.size(), result.size());
        assertEquals(owners.get(0).getFirstName(), result.get(0).firstName());

        verify(ownerRepository).getAll();
    }
}
