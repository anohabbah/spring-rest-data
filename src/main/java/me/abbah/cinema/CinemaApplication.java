package me.abbah.cinema;

import me.abbah.cinema.services.CinemaInitServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner {
    @Autowired
    private CinemaInitServiceContract dataSeeder;

    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        dataSeeder.initVilles();
        dataSeeder.initCinemas();
        dataSeeder.initSalles();
        dataSeeder.initPlaces();
        dataSeeder.initSeances();

        dataSeeder.initCategories();
        dataSeeder.initFilms();
        dataSeeder.initProjections();
        dataSeeder.initTickets();
    }
}
