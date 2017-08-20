package eu.solidcraft.film.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class InMemoryFilmRepository implements FilmRepository {
    private final ConcurrentHashMap<String, Film> map = new ConcurrentHashMap<>();

    @Override
    public Film save(Film film) {
        requireNonNull(film);
        map.put(film.dto().getTitle(), film);
        return film;
    }

    @Override
    public Film findOne(String title) {
        return map.get(title);
    }

    @Override
    public void delete(String title) {
        map.remove(title);
    }

    @Override
    public Page<Film> findAll(Pageable pageable) {
        List<Film> films = new ArrayList<>(map.values());
        return new PageImpl<>(films, pageable, films.size());
    }

}
