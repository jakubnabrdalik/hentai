package eu.solidcraft.hentai.films;

import eu.solidcraft.hentai.infrastructure.Profiles;
import org.springframework.context.annotation.Profile;
import org.springframework.data.rest.core.annotation.RestResource;

@Profile(Profiles.TEST)
public interface FilmCrudRepository extends FilmRepository {
    long count();

    @RestResource(exported = false)
    Film save(Film film);
}
