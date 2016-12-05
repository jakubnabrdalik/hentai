package eu.solidcraft.hentai.rentals;

import eu.solidcraft.hentai.films.FilmRepository;
import eu.solidcraft.hentai.films.FilmType;
import eu.solidcraft.hentai.infrastructure.TimeService;
import eu.solidcraft.hentai.users.User;
import eu.solidcraft.hentai.users.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

class RentCreator {
    private RentRepository rentRepository;
    private FilmRepository filmRepository;
    private UserRepository userRepository;
    private RentPriceCalculator rentPriceCalculator;

    RentCreator(RentRepository rentRepository, FilmRepository filmRepository, UserRepository userRepository, RentPriceCalculator rentPriceCalculator) {
        this.rentRepository = rentRepository;
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
        this.rentPriceCalculator = rentPriceCalculator;
    }

    @Transactional
    Rent rent(Long filmId, Integer numberOfDays, String username) {
        validateParams(filmId, numberOfDays, username);
        FilmType filmType = getFilmType(filmId);
        Rent rent = new Rent(filmId, filmType, numberOfDays, username, TimeService.now(), rentPriceCalculator);
        rentRepository.save(rent);
        giveBonusPoints(username, filmType);
        return rent;
    }

    private void giveBonusPoints(String username, FilmType filmType) {
        User user = userRepository.findOne(username);
        user.addBonusPoints(filmType.getBonusPoints());
        userRepository.save(user);
    }

    private void validateParams(Long filmId, Integer numberOfDays, String username) {
        notNull(filmId);
        notNull(numberOfDays);
        isTrue(numberOfDays > 0);
        hasText(username);
    }

    private FilmType getFilmType(Long filmId) {
        return Optional.of(filmRepository.findOne(filmId))
                .flatMap((film) -> Optional.of(film.getFilmType()))
                .orElseThrow(() -> new NoSuchFilmException(filmId));
    }
}
