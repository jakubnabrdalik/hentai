package eu.solidcraft.film.domain;

import eu.solidcraft.film.dto.FilmDto;
import lombok.Builder;

@Builder
class Film {
    private String title;
    private FilmType type;

    FilmDto dto() {
        return FilmDto.builder()
                .title(title)
                .type(type.dto())
                .build();
    }
}
