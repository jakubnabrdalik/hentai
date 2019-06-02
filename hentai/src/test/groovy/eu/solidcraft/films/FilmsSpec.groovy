package eu.solidcraft.films

import eu.solidcraft.films.dto.FilmDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class FilmsSpec extends Specification implements SampleFilms {
    FilmFacade filmFacade = new FilmConfiguration().filmFacade()
    Pageable pageRequest = new PageRequest(0, 10)

    def "should return list of films"() {
        given: "there are two films in inventory"
            filmFacade.add(film1, film2)
        when: "users asks for list of films"
            Page<FilmDto> films = filmFacade.findFilms(pageRequest)
        then: "module returns both films"
            films.content*.title.sort() == [film1, film2]*.title.sort()
    }

    def "should not add the same film twice"() {
        when: "user adds the same film twice"
            filmFacade.add(film1, film1)
        then: "module returns this film only once"
            filmFacade.findFilms(pageRequest).totalElements == 1
    }

    def "should return no films, when there are none"() {
        expect:
            filmFacade.findFilms(pageRequest).isEmpty()
    }

    def "should return film details"() {
        given: "inventory has films"
            filmFacade.add(film1, film2)
        when: "user asks for this film"
            Optional<FilmDto> filmDto = filmFacade.findOne(film1.title)
        then: "module returns this film"
            filmDto.get().title == film1.title
    }

    def "should not return film details for film not in inventory"() {
        when: "user asks for film not in inventory"
            Optional<FilmDto> filmDto = filmFacade.findOne("Commando")
        then: "module returns empty result"
            filmDto.isEmpty()
    }
}
