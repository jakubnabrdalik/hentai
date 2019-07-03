package eu.solidcraft.film;

import eu.solidcraft.film.dto.FilmDto;
import eu.solidcraft.film.dto.FilmTypeDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
class Film {
    @Id
    @Getter
    String title;
    FilmType type;

    FilmDto dto() {
        return new FilmDto(title, FilmTypeDto.valueOf(type.name()));
    }
}

enum FilmType {
    OLD, NEW, REGULAR
}