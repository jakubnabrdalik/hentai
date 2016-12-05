package base

import eu.solidcraft.hentai.RentalApplication
import eu.solidcraft.hentai.films.Film
import eu.solidcraft.hentai.films.FilmCrudRepository
import eu.solidcraft.hentai.infrastructure.Profiles
import eu.solidcraft.hentai.users.User
import eu.solidcraft.hentai.users.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.RememberMeAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Shared
import spock.lang.Specification

@Rollback
@Transactional
@ContextConfiguration(classes = RentalApplication)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles(Profiles.TEST)
abstract class IntegrationSpec extends Specification {
    protected static final String username = "seba"
    protected static final String password = "test"

    @Autowired
    protected FilmCrudRepository filmRepository

    @Autowired
    private AuthenticationManager authenticationManager

    @Autowired
    private UserRepository userRepository

    @Shared protected ArrayList<Film> persistedFilms = []

    void setup() {
        moviesArePresent()
        loginUser()
    }

    protected moviesArePresent() {
        if(filmRepository.count() == 0) {
            persistedFilms = TestData.films.collect {filmRepository.save(it)}
        }
    }

    private void loginUser() {
        User user = new User(username, password);
        userRepository.save(user)
        UserDetails userDetails = userRepository.loadUserByUsername(username)
        RememberMeAuthenticationToken rememberMeAuthenticationToken = new RememberMeAuthenticationToken("key", userDetails, null);
        rememberMeAuthenticationToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(rememberMeAuthenticationToken);
    }
}
