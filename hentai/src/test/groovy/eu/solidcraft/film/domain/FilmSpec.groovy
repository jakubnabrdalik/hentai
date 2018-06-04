package eu.solidcraft.film.domain

import eu.solidcraft.film.dto.FilmDto
import eu.solidcraft.film.dto.FilmNotFoundException
import eu.solidcraft.film.dto.FilmTypeDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class FilmSpec extends Specification {
    FilmFacade facade = new FilmConfiguration().filmFacade()
    FilmDto trumper = createFilmDto("50 shades of Trumpet", FilmTypeDto.NEW)
    FilmDto clingon = createFilmDto("American Clingon Bondage", FilmTypeDto.OLD)

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
            Page<FilmDto> foundFilms = facade.find(new PageRequest(0, 10))

        then: "system returns the films we have added"
            foundFilms.contains(trumper)
            foundFilms.contains(clingon)
    }

    private FilmDto createFilmDto(String title, FilmTypeDto type) {
        return FilmDto.builder().title(title).type(type).build()
    }
}
