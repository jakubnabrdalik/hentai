package eu.solidcraft.film

import eu.solidcraft.film.dto.FilmDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class FilmSpec extends Specification implements SampleFilms {
    FilmFacade filmFacade = new FilmConfiguration().filmFacade()

    def "should get film details"() {
        given: "module has film Rambo"
            filmFacade.add(rambo)
        when: "client asks for details of Rambo"
            Optional<FilmDto> foundFilm = filmFacade.showFilmDetails(rambo.title)
        then: "module returns details of Rambo"
            foundFilm.get().title == rambo.title
    }

    def "when no film found"() {
        when: "client asks for details of Rambo"
            Optional<FilmDto> foundFilm = filmFacade.showFilmDetails(rambo.title)
        then: "module does not return rambo"
            foundFilm.isEmpty()
    }

    def "should get films"() {
        given: "module has two films"
            filmFacade.add(rambo, commando)
        when: "client asks for films"
            Page<FilmDto> foundFilms = filmFacade.findFilms(new PageRequest(0, 10))
        then: "module returns both films"
            foundFilms.map{ it.title }.sort() == [rambo, commando]*.title.sort()
    }

}
