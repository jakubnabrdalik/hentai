package eu.solidcraft.films;

import eu.solidcraft.films.dto.FilmDto;
import eu.solidcraft.films.dto.FilmTypeDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
class Film {
    @Id
    @Getter
    String title;
    FilmType type;

    FilmDto dto() {
        return new FilmDto(title, FilmTypeDto.valueOf(type.name()));
    }

    boolean isTheSameFilm(Film film) {
        return title.equals(film.title);
    }
}

enum FilmType {
    OLD, NEW, REGULAR
}