package eu.solidcraft.films

import eu.solidcraft.films.dto.FilmDto
import eu.solidcraft.films.dto.FilmTypeDto
import groovy.transform.CompileStatic


@CompileStatic
trait SampleFilms {
    FilmDto film1 = new FilmDto("Rambo 5", FilmTypeDto.NEW)
    FilmDto film2 = new FilmDto("Rocky", FilmTypeDto.OLD)
}