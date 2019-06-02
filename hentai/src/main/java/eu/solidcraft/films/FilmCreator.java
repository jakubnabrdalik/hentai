package eu.solidcraft.films;

import eu.solidcraft.films.dto.FilmDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class FilmCreator {
    public Film create(FilmDto filmDto) {
        return Film.builder()
                .title(filmDto.getTitle())
                .type(FilmType.valueOf(filmDto.getType().name()))
                .build();
    }
}
