package acceptance.catalogue
import base.MvcIntegrationSpec
import base.TestData
import eu.solidcraft.hentai.films.Film
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.CoreMatchers.equalTo
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class FilmCatalogueSpec extends MvcIntegrationSpec {

    def "system shows details of a film"() {
        given:
            Film film = persistedFilms[0]
        when:
            ResultActions resultActions = mockMvc.perform(get("/films/" + film.id))
        then:
            resultActions.andExpect(status().isOk())
            hasDetailsOf(resultActions, film)
    }


    def "system shows list of films for rent"() {
        when:
            ResultActions resultActions = mockMvc.perform(get("/films"))

        then:
            resultActions.andExpect(status().isOk())
            hasDetailsOfFilms(resultActions)
    }

    private void hasDetailsOfFilms(ResultActions resultActions) {
        hasDetailsOf(resultActions, TestData.film0, 0)
        hasDetailsOf(resultActions, TestData.film1, 1)
        hasDetailsOf(resultActions, TestData.film2, 2)
    }

    private ResultActions hasDetailsOf(ResultActions resultActions, Film film, int position) {
        return hasDetailsOf(resultActions, film, getFilmJPathAt(position));
    }

    private ResultActions hasDetailsOf(ResultActions resultActions, Film film, String pathToFilm = "") {
        resultActions.
            andExpect(jsonPath(pathToFilm + 'filmType').value(equalTo(film.filmType.toString()))).
            andExpect(jsonPath(pathToFilm + 'title').value(equalTo(film.title))).
            andExpect(jsonPath(pathToFilm + 'description').value(equalTo(film.description)))
    }

    private String getFilmJPathAt(int position) {
        return "\$._embedded.films[$position]."
    }
}
