package eu.solidcraft.film.domain

import eu.solidcraft.film.dto.FilmDto
import eu.solidcraft.film.dto.FilmNotFoundException
import eu.solidcraft.film.dto.FilmTypeDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class FilmSpec extends Specification implements SampleFilms {
    FilmFacade facade = new FilmConfiguration().filmFacade()

    def "should show a film"() {
        given: "film is in the system"
            facade.add(trumper)

        expect: "system return the film"
            facade.show(trumper.title) == trumper
    }

    def "should throw exceptionw hen asked for a film that's not in the system"() {
        when: "system is asked for a film that is not present"
            facade.show("some title we don't have")
        then:
            thrown(FilmNotFoundException)
    }

    def "shoud list films"() {
        given: "we have two films in system"
            facade.add(trumper)
            facade.add(clingon)

        when: "we ask for all films"
            Page<FilmDto> foundFilms = facade.findAll(new PageRequest(0, 10))

        then: "system returns the films we have added"
            foundFilms.contains(trumper)
            foundFilms.contains(clingon)
    }
}
