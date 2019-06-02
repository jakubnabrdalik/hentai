package eu.solidcraft.films;

import eu.solidcraft.films.dto.FilmDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FilmFacade {
    FilmCreator filmCreator;
    FilmRepository filmRepository;

    public void add(FilmDto... films) {
        Arrays.stream(films)
                .map(filmCreator::create)
                .forEach(filmRepository::save);
    }

    public Page<FilmDto> findFilms(Pageable pageable) {
        return filmRepository.findAll(pageable)
                .map(Film::dto);
    }

    public Optional<FilmDto> findOne(String title) {
        return filmRepository.findByTitle(title)
                    .map(Film::dto);

    }
}
