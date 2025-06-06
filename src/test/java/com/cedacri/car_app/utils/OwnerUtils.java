package com.cedacri.car_app.utils;

import com.cedacri.car_app.entities.Owner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.cedacri.car_app.utils.CarUtils.getPreparedCarList;

public class OwnerUtils {
    private static final Owner owner = new Owner();

    public static Owner getPreparedOwner() {
        owner.setFirstName("Maria");
        owner.setLastName("Bujor");
        owner.setCars(getPreparedCarList());

        return owner;
    }

    public static Owner getPreparedOwnerWithOutCar() {
        return new Owner(null, "Maria", "Cojocaru", Collections.emptyList());
    }

    public static List<Owner> getPreparedOwnersList() {
        return new ArrayList<>(List.of(
                new Owner("c3b0d2a2-cf75-4745-a0b6-6c9d83d83d4d", "Victor", "Cioban", getPreparedCarList()),
                new Owner("c3b0d2a2-cf75-4745-a0b6-6c988d65f6vc", "Dumitru", "Cojocaru", getPreparedCarList())
        ));
    }

    public static List<Owner> getPreparedOwnersListWithoutCars() {
        return new ArrayList<>(List.of(
                new Owner(null,"Victor", "Cioban", Collections.emptyList()),
                new Owner(null,"Dumitru", "Cojocaru", Collections.emptyList())
        ));
    }
}
