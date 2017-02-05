package eu.solidcraft.film.domain;

import eu.solidcraft.film.dto.FilmNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

interface FilmRepository extends Repository<Film, String> {
    Film save(Film film);
    Film findOne(String title);
    void delete(String title);
    Page<Film> findAll(Pageable pageable);

    default Film findOneOrThrow(String title) {
        Film film = findOne(title);
        if(film == null) {
            throw new FilmNotFoundException(title);
        }
        return film;
    }
}
