package me.abbah.cinema.web;

import lombok.Data;
import me.abbah.cinema.dao.FilmRepository;
import me.abbah.cinema.dao.TicketRepository;
import me.abbah.cinema.entities.Film;
import me.abbah.cinema.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CinemaRestController {
    @Autowired
    private FilmRepository films;

    @Autowired
    private TicketRepository tickets;

    @GetMapping(path = "/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable(name = "id") Long id) throws IOException {
        final Optional<Film> film = this.films.findById(id);
        if (film.isEmpty()) {
            return null;
        }

        final String photoName = film.get().getPhoto();
        final File file = new File(System.getProperty("user.home" + "/Documents/IdeaProjects/cinema/images" + photoName + "jpg"));
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    @PostMapping("/tickets")
    @Transactional
    public List<Ticket> buyTickets(@RequestBody TicketForm ticketForm) {
        return ticketForm.getTickets()
                .stream()
                .map(ticketId -> this.tickets.findById(ticketId))
                .filter(Optional::isPresent)
                .map(ticket -> {
                    final Ticket ticket1 = ticket.get();
                    ticket1.setClientName(ticketForm.getClientName());
                    ticket1.setReservee(true);
                    ticket1.setCodePayement(ticketForm.getCodePayement());
                    this.tickets.save(ticket1);
                    return ticket1;
                })
                .collect(Collectors.toUnmodifiableList());
    }
}

@Data
class TicketForm {
    private String clientName;
    private int codePayement;
    private List<Long> tickets = new ArrayList<>();
}
