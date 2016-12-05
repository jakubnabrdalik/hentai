package eu.solidcraft.hentai.rentals;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;

@RepositoryRestResource
interface RentRepository extends Repository<Rent, Long> {

    @PostAuthorize("returnObject?.username == principal.username")
    Rent findOne(Long id);

    @RestResource(exported = false)
    Rent save(Rent rent);
}
