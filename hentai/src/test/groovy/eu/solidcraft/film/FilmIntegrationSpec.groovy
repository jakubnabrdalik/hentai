package eu.solidcraft.film

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
    def "should get films"() {
        given: "module has two films"
            filmFacade.add(commando, rambo)

        when: "client asks for all films"
            ResultActions films = mockMvc.perform(get("/film"))

        then: "module returns both films"
            films.andExpect(status().isOk())
            films.andExpect(content().json("""
            {
                "content": [
                    { "title": "$rambo.title" },
                    { "title": "$commando.title" }
                ]
            }
            """))

        when: "client asks for details of Rambo"
        then: "module return first Rambo"
    }
}
