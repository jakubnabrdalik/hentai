package acceptance

import base.MvcIntegrationSpec
import eu.solidcraft.hentai.films.Film
import eu.solidcraft.hentai.films.FilmType
import eu.solidcraft.hentai.users.UserRepository
import groovy.json.JsonSlurper
import org.hamcrest.Matcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.CoreMatchers.any
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.core.IsNull.notNullValue
import static org.hamcrest.core.IsNull.nullValue
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class RentingSpec extends MvcIntegrationSpec {
    @Autowired
    UserRepository userRepository

    def "customer can rent films"() {
        given: "there is a film in catalogue"
            Film film = persistedFilms[0]

        when: "renting a film"
            ResultActions resultActions = rentAFilm(film)

        then: "we get HTTP status 200 + rentId"
            resultActions.
                    andExpect(status().isOk()).
                    andExpect(jsonPath('rentId').value(any(Integer)));

        when: "we search for the rent"
            long rentId = getRentId(resultActions)
            resultActions = mockMvc.perform(get("/rents/$rentId"))

        then: "it's the rent for the film we wanted"
            rentHasReturnedOn(resultActions, film, nullValue())

        when: "we return the film"
            mockMvc.perform(post('/returns').
                accept(MediaType.APPLICATION_JSON).
                param("rentId", rentId.toString())).
                andExpect(status().isOk()).
                andExpect(jsonPath('surcharge').value(any(Integer)));

        and: "we search for the rent again"
            resultActions = mockMvc.perform(get("/rents/$rentId"))

        then: "the film is returned"
            rentHasReturnedOn(resultActions, film, notNullValue())
    }

    def "renting a new release should give 2 points per rental"() {
        given:
          Film film = persistedFilms.find {it.filmType == FilmType.NEW_RELEASE}

        when:
            rentAFilm(film)

        then:
            userRepository.findOne(username).getBonusPoints() == 2
    }

    def "renting non-new release should give one point per rental"() {
        given:
            Film film = persistedFilms.find {it.filmType != FilmType.NEW_RELEASE}

        when:
            rentAFilm(film)

        then:
            userRepository.findOne(username).getBonusPoints() == 1
    }

    private ResultActions rentHasReturnedOn(ResultActions resultActions, Film film, Matcher<Object> returnedOnMatcher) {
        return resultActions.
                andExpect(status().isOk()).
                andExpect(jsonPath('filmId').value(equalTo(film.id.toInteger()))).
                andExpect(jsonPath('returnedOn').value(returnedOnMatcher))
    }

    private ResultActions rentAFilm(Film film) {
        ResultActions resultActions = mockMvc.perform(post('/rents').
                accept(MediaType.APPLICATION_JSON).
                param("filmId", film.id.toString()).
                param("numberOfDays", "2").
                param("username", username))
        return resultActions
    }

    private long getRentId(ResultActions resultActions) {
        def jsonResult = new JsonSlurper().parseText(resultActions.andReturn().response.getContentAsString())
        Long rentId = jsonResult.rentId
        return rentId
    }
}
