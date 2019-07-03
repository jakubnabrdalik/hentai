package eu.solidcraft.film

import eu.solidcraft.film.dto.FilmDto
import eu.solidcraft.film.dto.FilmTypeDto
import groovy.transform.SelfType
import spock.lang.Specification

@SelfType(Specification)
trait SampleFilms {
    FilmDto rambo = new FilmDto("Rambo", FilmTypeDto.NEW)
    FilmDto commando = new FilmDto("Commando", FilmTypeDto.OLD)
}
