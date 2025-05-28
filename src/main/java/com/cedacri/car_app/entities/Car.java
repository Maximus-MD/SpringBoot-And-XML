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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
@Column(name = "car_id", unique = true, nullable = false)
    private String vinCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "year", nullable = false)
    private int date;

    @Column(name = "engine_volume")
    private int engineVolume;

    @Column(name = "engine_power", nullable = false)
    private int enginePower;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelTypeEnum fuelType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transmission")
    private TransmissionEnum transmission;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CarTypeEnum type;

    @Column(name = "seats_num", nullable = false)
    private int numSeats;

    @Column(name = "doors_num", nullable = false)
    private int doorsNum;

    @Column(name = "max_speed")
    private int maxSpeed;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
}