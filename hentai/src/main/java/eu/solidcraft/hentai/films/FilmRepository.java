package eu.solidcraft.hentai.films;

import eu.solidcraft.hentai.infrastructure.Profiles;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
@Profile({Profiles.DEV, Profiles.PROD})
public interface FilmRepository extends Repository<Film, Long> {

    Page<Film> findAll(Pageable pageable);

    //Cannot declare as Optional<Film>, because Spring Data MVC doesn't handle it. Bugger.
    Film findOne(Long id);
}
