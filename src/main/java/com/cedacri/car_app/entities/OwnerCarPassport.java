package com.cedacri.car_app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "owner_car_passport")
public class OwnerCarPassport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passport_id", nullable = false)
    private CarPassport passport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
}
