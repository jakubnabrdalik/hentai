package eu.solidcraft.film.domain;

import eu.solidcraft.film.dto.FilmDto;
import eu.solidcraft.infrastructure.metrics.logging.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;

@Transactional
@Log
public class FilmFacade {
    private FilmRepository filmRepository;
    private FilmCreator filmCreator;

    public FilmFacade(FilmRepository filmRepository, FilmCreator filmCreator) {
        this.filmRepository = filmRepository;
        this.filmCreator = filmCreator;
    }

    public FilmDto add(FilmDto filmDto) {
        requireNonNull(filmDto);
        Film film = filmCreator.from(filmDto);
        film =  filmRepository.save(film);
        return film.dto();
    }

    public FilmDto show(String filmDto) {
        requireNonNull(filmDto);
        Film film = filmRepository.findOneOrThrow(filmDto);
        return film.dto();
    }

    public Page<FilmDto> findAll(Pageable pageable) {
        requireNonNull(pageable);
        return filmRepository
                .findAll(pageable)
                .map(film -> film.dto());
    }
}
