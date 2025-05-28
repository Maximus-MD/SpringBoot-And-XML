package com.cedacri.car_app.utils;

import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.entities.enums.CarTypeEnum;
import com.cedacri.car_app.entities.enums.FuelTypeEnum;
import com.cedacri.car_app.entities.enums.TransmissionEnum;

import java.util.ArrayList;
import java.util.List;

public class CarUtils {
    private static final Car preparedCar = new Car();
    private static final Car preparedTruck = new Car();

    public static Car getPreparedCar() {
        preparedCar.setVinCode("WDBUF56X48B123654");
        preparedCar.setName("Mazda");
        preparedCar.setModel("RX-7");
        preparedCar.setDate(1997);
        preparedCar.setEngineVolume(1300);
        preparedCar.setEnginePower(280);
        preparedCar.setType(CarTypeEnum.COUPE);
        preparedCar.setFuelType(FuelTypeEnum.PETROL);
        preparedCar.setTransmission(TransmissionEnum.MANUAL);
        preparedCar.setNumSeats(4);
        preparedCar.setDoorsNum(2);
        preparedCar.setMaxSpeed(260);

        return preparedCar;
    }

    public static Car getPreparedTruck() {
        preparedTruck.setVinCode("WDBUF56X48B123777");
        preparedTruck.setName("Volvo");
        preparedTruck.setModel("FH16");
        preparedTruck.setDate(1997);
        preparedTruck.setEngineVolume(11000);
        preparedTruck.setEnginePower(480);
        preparedTruck.setFuelType(FuelTypeEnum.DIESEL);
        preparedTruck.setTransmission(TransmissionEnum.AUTOMATIC);
        preparedTruck.setNumSeats(4);
        preparedTruck.setDoorsNum(2);
        preparedTruck.setMaxSpeed(90);

        return preparedTruck;
    }

    public static List<Car> getPreparedCarList() {
        return new ArrayList<>(List.of(
                getPreparedCar(),
                getPreparedTruck()
        ));
    }
}
