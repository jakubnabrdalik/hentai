package eu.solidcraft.film.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class FilmDto {
    private String title;
    private FilmTypeDto type;

}
