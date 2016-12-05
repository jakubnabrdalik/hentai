package acceptance.catalogue
import base.MvcIntegrationSpec
import base.TestData
import org.hamcrest.Matcher
import spock.lang.Unroll

import static org.hamcrest.CoreMatchers.anyOf
import static org.hamcrest.CoreMatchers.containsString
import static org.hamcrest.core.AllOf.allOf
import static org.hamcrest.core.IsNot.not
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class FilmCataloguePagingSpec extends MvcIntegrationSpec {

    @Unroll
    def "with test data sorted #direction by id, page limited to size of 1, page #page has only the movie #title"() {
        expect:
            mockMvc.perform(get(filmsPageUrl(page, direction))).
                andExpect(status().isOk()).
                andExpect(content().string(allOf(
                        [containsString(title), not(anyOf(otherFilmsThan(title)))]
                )))
        where:
            page | direction  || title
            0    | "asc"      || TestData.film0.title
            1    | "asc"      || TestData.film1.title
            0    | "desc"     || TestData.film2.title
            1    | "desc"     || TestData.film1.title
    }

    private Iterable<Matcher> otherFilmsThan(String title) {
        return persistedFilms.findAll {it.title != title}.collect {containsString(it.title)}
    }

    private String filmsPageUrl(int page, String direction) {
        return "/films?page=$page&size=1&sort=id,$direction"
    }
}
