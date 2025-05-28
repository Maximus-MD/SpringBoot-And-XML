package com.cedacri.car_app;

import com.cedacri.car_app.repositories.CarRepository;
import com.cedacri.car_app.repositories.impl.CarRepositoryImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarAppApplication {

    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        CarRepository repo = new CarRepositoryImpl(sessionFactory);
        SpringApplication.run(CarAppApplication.class, args);
    }

}
