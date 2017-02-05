package eu.solidcraft.film.domain;

import eu.solidcraft.film.dto.FilmDto;

import static java.util.Objects.requireNonNull;

class FilmCreator {
    Film from(FilmDto filmDto) {
        requireNonNull(filmDto);
        return Film.builder()
                .title(filmDto.getTitle())
                .type(FilmType.valueOf(filmDto.getType().name()))
                .build();
    }
}
