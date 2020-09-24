package me.abbah.cinema.dao;

import me.abbah.cinema.entities.Salle;
import me.abbah.cinema.entities.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SalleRepository extends JpaRepository<Salle, Long> {
}
