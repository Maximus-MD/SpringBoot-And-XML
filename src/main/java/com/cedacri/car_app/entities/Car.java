package com.cedacri.car_app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(name = "fuel_type", nullable = false)
    private String fuelType;

    @Column(name = "transmission")
    private String transmission;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "num_seats", nullable = false)
    private int numSeats;

    @Column(name = "max_speed")
    private int maxSpeed;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
}