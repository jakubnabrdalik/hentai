package eu.solidcraft.film

import eu.solidcraft.base.IntegrationSpec
import eu.solidcraft.film.domain.FilmFacade
import eu.solidcraft.film.domain.SampleFilms
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class FilmControllerAcceptanceSpec extends IntegrationSpec implements SampleFilms {

    @Autowired
    FilmFacade filmFacade

    @WithMockUser
    def "should get films"() {
        given: 'inventory has "American Clingon Bondage"'
            filmFacade.add(trumper)
            filmFacade.add(clingon)

        when: 'I go to /films'
            ResultActions getFilms = mockMvc.perform(get("/films"))

        then: 'I see all films'
            getFilms.andExpect(status().isOk())
                .andExpect(content().json("""
                {
                    "content": [
                        {"title":"$clingon.title","type":"$clingon.type"},
                        {"title":"$trumper.title","type":"$trumper.type"}
                    ]
                }"""))

        when: 'I go to /film/'
            ResultActions getFilm = mockMvc.perform(get("/film/$clingon.title"))

        then: 'I see details of that film'
            getFilm.andExpect(status().isOk())
                .andExpect(content().json("""
                        {"title":"$clingon.title","type":"$clingon.type"},
                """))
    }
}
