package eu.solidcraft

import eu.solidcraft.base.IntegrationSpec
import eu.solidcraft.rent.RentFacade
import org.springframework.beans.factory.annotation.Autowired

class EventsSpec extends IntegrationSpec {
    @Autowired RentFacade rentFacade

    def "should fire event and see logs"() {
        when:
            rentFacade.rent("Rambo", 5)
        then:
            notThrown(RuntimeException)
    }
}
