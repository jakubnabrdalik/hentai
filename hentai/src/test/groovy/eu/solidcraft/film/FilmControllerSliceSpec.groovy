package eu.solidcraft.film

import eu.solidcraft.film.domain.FilmFacade
import eu.solidcraft.film.domain.SampleFilms
import eu.solidcraft.film.dto.FilmNotFoundException
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
    def "asking for non existing film should return 404"() {
        given: "there is no film with the title I want"
            String nonExistingTitle = "NonExisitngTitle"
            filmFacade.show(nonExistingTitle) >> { throw new FilmNotFoundException(nonExistingTitle) }

        expect: "I get 404 and a message"
            mockMvc.perform(get("/film/$nonExistingTitle"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                    {
                        "message": "No film of title NonExisitngTitle found"
                    } """))
    }

    //We could have slice tests for all controller methods, but consider if it makes sense

    @WithMockUser
    def "should get films"() {
        given: 'inventory has two films'
            filmFacade.findAll(_) >> { Pageable pageable -> new PageImpl([trumper, clingon], pageable, 2) }

        when: 'I go to /films'
            ResultActions getFilms = mockMvc.perform(get("/films"))

        then: 'I see list of those films'
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

        when: 'I go to /film'
            ResultActions getFilm = mockMvc.perform(get("/film/$clingon.title"))

        then: 'I see details of that film'
            getFilm.andExpect(status().isOk())
                .andExpect(content().json("""
                        {"title":"$clingon.title","type":"$clingon.type"},
                """, false))
    }
}
