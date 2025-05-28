package com.cedacri.car_app.utils;

import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.entities.enums.CarTypeEnum;
import com.cedacri.car_app.entities.enums.FuelTypeEnum;
import com.cedacri.car_app.entities.enums.TransmissionEnum;

public class CarUtils {
    private static final Car preparedCar = new Car();

    public static Car getPreparedCar(){
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

    public static Car getPreparedTruck(){
        preparedCar.setVinCode("WDBUF56X48B123654");
        preparedCar.setName("Volvo");
        preparedCar.setModel("FH16");
        preparedCar.setDate(1997);
        preparedCar.setEngineVolume(11000);
        preparedCar.setEnginePower(480);
        preparedCar.setFuelType(FuelTypeEnum.DIESEL);
        preparedCar.setTransmission(TransmissionEnum.AUTOMATIC);
        preparedCar.setNumSeats(4);
        preparedCar.setDoorsNum(2);
        preparedCar.setMaxSpeed(90);

        return preparedCar;
    }
}
