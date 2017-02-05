package eu.solidcraft.film.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FilmConfiguration {

    FilmFacade filmFacade() {
        return filmFacade(new InMemoryFilmRepository());
    }

    @Bean
    FilmFacade filmFacade(FilmRepository filmRepository) {
        FilmCreator filmCreator = new FilmCreator();
        return new FilmFacade(filmRepository, filmCreator);
    }
}
