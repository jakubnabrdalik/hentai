package eu.solidcraft.base

import eu.solidcraft.MyAppRunner
import eu.solidcraft.infrastructure.config.AppProfiles
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity

@ActiveProfiles(profiles = [AppProfiles.INTEGRATION])
@SpringBootTest(classes = [MyAppRunner.class])
abstract class IntegrationSpec extends Specification {
    @Autowired
    protected WebApplicationContext webApplicationContext

    MockMvc mockMvc

    @Before
    void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                        .apply(springSecurity())
                        .build()
    }
}
