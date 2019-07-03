package eu.solidcraft.film.dto;

import lombok.Value;

@Value
public class FilmDto {
    String title;
    FilmTypeDto type;
}
