package eu.solidcraft.hentai.rentals
import eu.solidcraft.hentai.films.FilmType
import eu.solidcraft.hentai.infrastructure.TimeService
import org.springframework.mock.env.MockEnvironment
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class RentPricingSpec extends Specification {
    String premiumPrice = '40'
    String basePrice = '30'
    RentPriceCalculator priceCalculator

    def setup() {
        MockEnvironment environment = new MockEnvironment().
                withProperty('renting.price.PREMIUM', premiumPrice).
                withProperty('renting.price.BASIC', basePrice)
        priceCalculator = new RentPriceCalculator(environment)
    }

    @Unroll
    def "for filmType #filmType and numberOfDays #numberOfDays, calculated price should be #price"() {
        expect:
            priceCalculator.calculatePrice(filmType, numberOfDays) == price

        where:
            filmType             | numberOfDays | price
            FilmType.NEW_RELEASE | 1            | 40
            FilmType.REGULAR     | 5            | 90
            FilmType.REGULAR     | 2            | 30
            FilmType.OLD         | 7            | 90
    }

    @Unroll
    def "returning a filmType #filmType, #additionlDaysOfRent after due date, should result in #price surcharge"() {
        given:
            Long filmId = 1
            LocalDate rentedOn = TimeService.now()
            Rent rent = new Rent(filmId, filmType, initialNumberOfDaysForRent, "Seba", rentedOn, priceCalculator)
        when:
            rent.returned(rentedOn.plusDays(initialNumberOfDaysForRent + additionlDaysOfRent), priceCalculator)
        then:
            rent.lateReturnSurgcharge == price

        where:
            filmType             | initialNumberOfDaysForRent | additionlDaysOfRent | price
            FilmType.NEW_RELEASE | 5                          | 2                   | 80
            FilmType.REGULAR     | 3                          | 1                   | 30
    }


}
