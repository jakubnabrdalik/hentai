package eu.solidcraft.hentai.rentals;

import eu.solidcraft.hentai.films.FilmRepository;
import eu.solidcraft.hentai.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
class RentConfiguration {
    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public RentCreator rentCreator(Environment environment) {
        return new RentCreator(rentRepository, filmRepository, userRepository, rentPriceCalculator(environment));
    }

    @Bean
    public RentReturner rentReturner(Environment environment) {
        return new RentReturner(rentRepository, rentPriceCalculator(environment));
    }

    @Bean
    public RentPriceCalculator rentPriceCalculator(Environment environment) {
        return new RentPriceCalculator(environment);
    }
}
