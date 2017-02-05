package eu.solidcraft.film

import eu.solidcraft.film.domain.FilmFacade
import eu.solidcraft.film.domain.SampleFilms
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest([FilmController, FilmTestConfig])
class FilmControllerSliceSpec extends Specification implements SampleFilms {

    @TestConfiguration
    static class FilmTestConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory();

        @Bean
        FilmFacade filmFacade() {
            return detachedMockFactory.Stub(FilmFacade)
        }
    }

    @Autowired
    FilmFacade filmFacade

    @Autowired
    MockMvc mockMvc

    @WithMockUser
    def "should get films"() {
        given: 'inventory has "American Clingon Bondage"'
            filmFacade.findAll(_) >> { Pageable pageable -> new PageImpl([trumper, clingon], pageable, 2) }

        when: 'I go to /film'
            ResultActions getFilms = mockMvc.perform(get("/films"))

        then: 'I see details'
            getFilms.andExpect(status().isOk())
                .andExpect(content().json("""
                { 
                    "content": [
                        {"title":"$clingon.title","type":"$clingon.type"},
                        {"title":"$trumper.title","type":"$trumper.type"}
                    ]
                }""", false))
    }

    @WithMockUser
    def "should get film"() {
        given: 'inventory has an old film "American Clingon Bondage" and a new release of "50 shades of Trumpet"'
            filmFacade.show(clingon.title) >> clingon

        when: 'I go to /films'
            ResultActions getFilm = mockMvc.perform(get("/film/$clingon.title"))

        then: 'I see film'
            getFilm.andExpect(status().isOk())
                .andExpect(content().json("""
                        {"title":"$clingon.title","type":"$clingon.type"},
                """, false))
    }
}
