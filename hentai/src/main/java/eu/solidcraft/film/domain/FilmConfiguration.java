package eu.solidcraft.film.domain;

class FilmConfiguration {
    FilmFacade filmFacade() {
        InMemoryFilmRepository filmRepository = new InMemoryFilmRepository();
        FilmCreator filmCreator = new FilmCreator();
        return new FilmFacade(filmRepository, filmCreator);
    }
}
