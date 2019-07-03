package eu.solidcraft.film;

import eu.solidcraft.film.dto.FilmDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class FilmCreator {
    Film create(FilmDto dto) {
        return Film.builder()
                .title(dto.getTitle())
                .type(FilmType.valueOf(dto.getType().name()))
                .build();
    }
}
