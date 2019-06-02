package eu.solidcraft.films

import eu.solidcraft.base.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class FilmIntegrationSpec extends IntegrationSpec implements SampleFilms {
    @Autowired FilmFacade filmFacade

    @WithMockUser
    def "should return films"() {
        given: "there are two films in inventory"
            filmFacade.add(film1, film2)

        when: "user asks for films"
            ResultActions getFilmsResult = mockMvc.perform(get("/film"))

        then: "module returns both films"
            getFilmsResult
                    .andExpect(status().isOk())
                    .andExpect(content().json("""
                    {
                        "content": [
                            { "title": "$film1.title" },
                            { "title": "$film2.title" }
                        ]
                    }
                    """))

        when: "user asks for details of first film"
            ResultActions getFilmDetailsResult =
                    mockMvc.perform(get("/film/$film1.title"))

        then: "module returns first film"
            getFilmDetailsResult
                    .andExpect(status().isOk())
                    .andExpect(content().json("""
                    {
                        "title": "$film1.title"
                    }
                    """))

        expect: "user asks for non existing film, module returns 404"
            mockMvc.perform(get("/film/commando"))
                .andExpect(status().isNotFound())
    }
}
