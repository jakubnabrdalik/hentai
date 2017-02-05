package eu.solidcraft.film.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class InMemoryFilmRepository implements FilmRepository {
    private ConcurrentHashMap<String, Film> map = new ConcurrentHashMap<>();

    @Override
    public Film save(Film film) {
        requireNonNull(film);
        map.put(film.dto().getTitle(), film);
        return film;
    }

    @Override
    public Film findById(String title) {
        return map.get(title);
    }

    @Override
    public Page<Film> findAll(Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(map.values()), pageable, map.size());
    }
}
