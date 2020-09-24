package me.abbah.cinema.services;

import me.abbah.cinema.dao.*;
import me.abbah.cinema.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class DataSeedersService implements CinemaInitServiceContract {
    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ProjectionRepository projectionRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CategorieRepository catRep;

    @Override
    public void initVilles() {
        Stream.of("Abidjan", "Bonoua", "Aboisso", "Daloa", "Daoukro", "Bouake", "San Pedro", "Yamoussokro", "Korhogo")
                .forEach(villeName -> {
                    Ville ville = new Ville();
                    ville.setName(villeName);
                    this.villeRepository.save(ville);
                });
    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(ville -> {
            Stream.of("Cinema Samo", "Majestic Cinema - Sococe", "Cinema Gaumont")
                    .forEach(cinemaName -> {
                        Cinema cinema = new Cinema();
                        cinema.setName(cinemaName);
                        cinema.setVille(ville);
                        cinema.setNbreSalles((int) (3 + Math.random() * 7));
                        this.cinemaRepository.save(cinema);
                    });
        });
    }

    @Override
    public void initSalles() {
        this.cinemaRepository.findAll().forEach(cinema -> {
            for (int i = 0; i < cinema.getNbreSalles(); i++) {
                Salle salle = new Salle();
                salle.setName("Salle " + (i + 1));
                salle.setCinema(cinema);
                salle.setNbrePlaces((int) (15 + Math.random() * 20));
                this.salleRepository.save(salle);
            }
        });
    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle -> {
            for (int i = 0; i < salle.getNbrePlaces(); i++) {
                Place place = new Place();
                place.setNumero(String.valueOf(i + 1));
                place.setSalle(salle);
                this.placeRepository.save(place);
            }
        });
    }

    @Override
    public void initSeances() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        Stream.of("12:00", "15:00", "17:00", "21:00").forEach(dtStr -> {
            try {
                Seance seance = new Seance();
                seance.setStartTime(df.parse(dtStr));
                seanceRepository.save(seance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initCategories() {
        Stream.of("Action", "Fiction", "Drama", "Histoire", "Comédie").forEach(cat -> {
            Categorie categorie = new Categorie(null, cat, null);
            this.catRep.save(categorie);
        });
    }

    @Override
    public void initFilms() {
        List<Categorie> categories = this.catRep.findAll();

        Stream.of("Game of thrones", "Seigneur des anneaux", "Iron man", "Cat woman")
                .forEach(filmName -> {
                    Film film = new Film();
                    film.setTitle(filmName);
                    film.setDuration(1 + new Random().nextDouble() * 2);
                    film.setCategorie(categories.get(new Random().nextInt(categories.size())));
                    this.filmRepository.save(film);
                });
    }

    @Override
    public void initProjections() {

    }

    @Override
    public void initTickets() {

    }
}
