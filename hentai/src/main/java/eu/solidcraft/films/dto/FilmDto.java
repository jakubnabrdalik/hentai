package eu.solidcraft.films.dto;

import lombok.Value;

@Value
public class FilmDto {
    String title;
    FilmTypeDto type;
}
