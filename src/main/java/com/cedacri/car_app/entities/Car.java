package com.cedacri.car_app.entities;

import com.cedacri.car_app.entities.enums.CarTypeEnum;
import com.cedacri.car_app.entities.enums.FuelTypeEnum;
import com.cedacri.car_app.entities.enums.TransmissionEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars", uniqueConstraints = {
        @UniqueConstraint(columnNames = "car_id")})
public class Car {
    @Id
    @Column(name = "car_id", unique = true, nullable = false)
    private String vinCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "manufacture_year", nullable = false)
    private Integer manufactureYear;

    @Column(name = "engine_volume")
    private Integer engineVolume;

    @Column(name = "engine_power", nullable = false)
    private Integer enginePower;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelTypeEnum fuelType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transmission")
    private TransmissionEnum transmission;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CarTypeEnum type;

    @Column(name = "seats_num", nullable = false)
    private Integer numSeats;

    @Column(name = "doors_num", nullable = false)
    private Integer doorsNum;

    @Column(name = "max_speed")
    private Integer maxSpeed;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @ToString.Exclude
    private Owner owner;
}