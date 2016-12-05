package eu.solidcraft.hentai.rentals;

import eu.solidcraft.hentai.infrastructure.TimeService;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.springframework.util.Assert.notNull;

class RentReturner {
    private RentRepository rentRepository;
    private RentPriceCalculator rentPriceCalculator;

    RentReturner(RentRepository rentRepository, RentPriceCalculator rentPriceCalculator) {
        this.rentRepository = rentRepository;
        this.rentPriceCalculator = rentPriceCalculator;
    }

    @Transactional
    Rent returnFilm(@RequestParam Long rentId) {
        notNull(rentId);
        Rent rent = Optional.of(rentRepository.findOne(rentId)).orElseThrow(() -> new NoSuchRentException(rentId));
        rent.returned(TimeService.now(), rentPriceCalculator);
        rentRepository.save(rent);
        return rent;
    }

}
