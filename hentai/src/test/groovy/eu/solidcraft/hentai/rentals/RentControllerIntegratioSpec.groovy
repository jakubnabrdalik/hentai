package eu.solidcraft.hentai.rentals
import base.IntegrationSpec
import groovy.transform.TypeChecked
import org.springframework.beans.factory.annotation.Autowired

@TypeChecked
class RentControllerIntegratioSpec extends IntegrationSpec {
    @Autowired RentController rentController
    @Autowired RentRepository rentRepository

    def "renting should save rent in database"() {
        when:
            Map model = rentController.rent(persistedFilms[0].id, 2, username)
        then:
            rentRepository.findOne(model["rentId"]) != null
    }
}
