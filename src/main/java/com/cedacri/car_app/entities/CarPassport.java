package com.cedacri.car_app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passports")
public class CarPassport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passport_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name = "name", nullable = false)
    private String carName;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "year", nullable = false)
    private String date;

    @Column(name = "engine_volume")
    private int engineVolume;

    @Column(name = "engine_power", nullable = false)
    private int enginePower;

    @Column(name = "fuel_type", nullable = false)
    private String fuelType;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "num_seats", nullable = false)
    private int numSeats;
}