package eu.solidcraft.film;

import eu.solidcraft.film.dto.FilmDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FilmFacade {
    FilmCreator filmCreator;
    FilmRepository filmRepository;

    public void add(FilmDto... dtos) {
        Arrays.stream(dtos)
                .filter(Objects::nonNull)
                .map(filmCreator::create)
                .forEach(filmRepository::save);
    }

    public Optional<FilmDto> showFilmDetails(@NonNull String title) {
        return filmRepository.findByTitle(title)
                .map(Film::dto);
    }

    public Page<FilmDto> findFilms(Pageable pageable) {
        return filmRepository.findAll(pageable)
                .map(Film::dto);
    }
}
