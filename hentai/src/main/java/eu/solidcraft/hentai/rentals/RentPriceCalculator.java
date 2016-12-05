package eu.solidcraft.hentai.rentals;

import eu.solidcraft.hentai.films.FilmType;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;

import static org.springframework.util.Assert.notNull;

class RentPriceCalculator {
    private Environment environment;

    RentPriceCalculator(Environment environment) {
        this.environment = environment;
    }

    BigDecimal calculatePrice(FilmType filmType, Integer numberOfDays) {
        notNull(filmType); notNull(numberOfDays);
        BigDecimal priceForFirstDay = getPriceForFirstDay(filmType);
        return filmType.calculatePrice(priceForFirstDay, numberOfDays);
    }

    private BigDecimal getPriceForFirstDay(FilmType filmType) {
        BigDecimal priceForFirstDay = environment.getProperty("renting.price." + filmType.getPriceType().toString(), BigDecimal.class);
        validatePriceForFirstDay(priceForFirstDay, filmType);
        return priceForFirstDay;
    }

    private void validatePriceForFirstDay(BigDecimal priceForFirstDay, FilmType filmType) {
        if(priceForFirstDay == null) {
            throw new NoPriceForFilmTypeConfiguredException("No price configured for film type", filmType);
        }
    }
}

class NoPriceForFilmTypeConfiguredException extends RuntimeException {
    private FilmType filmType;

    public NoPriceForFilmTypeConfiguredException(String message, FilmType filmType) {
        super(message);
        this.filmType = filmType;
    }
}
