package eu.solidcraft.films;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

interface FilmRepository extends Repository<Film, String> {
    Page<Film> findAll(Pageable pageable);
    void save(Film film);
    Optional<Film> findByTitle(String title);
}

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class InMemoryFilmRepository implements FilmRepository {
    List<Film> store = new ArrayList<>();

    @Override
    public void save(Film film) {
        if (storeDoesNotHaveThisFilm(film)) {
            store.add(film);
        }
    }

    @Override
    public Optional<Film> findByTitle(String title) {
        return store.stream()
                .filter(film -> film.getTitle().equals(title))
                .findFirst();

    }

    private boolean storeDoesNotHaveThisFilm(Film film) {
        return store.stream()
                .noneMatch(film::isTheSameFilm);
    }

    @Override
    public Page<Film> findAll(Pageable pageable) {
        long leftFilms = store.size() - pageable.getOffset();
        long filmsOnThisPage = Math.min(leftFilms, pageable.getPageSize());
        List<Film> filmOnPage =
                store.subList((int) pageable.getOffset(), (int)filmsOnThisPage);
        return new PageImpl<>(filmOnPage, pageable, store.size());
    }

}
