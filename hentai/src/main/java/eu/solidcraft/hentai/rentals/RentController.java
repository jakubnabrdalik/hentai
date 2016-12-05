package eu.solidcraft.hentai.rentals;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
class RentController {
    private RentCreator rentCreator;
    private RentReturner rentReturner;

    @Autowired
    RentController(RentCreator rentCreator, RentReturner rentReturner) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        Optional<Integer> sum = numbers.stream()
                .reduce(Integer::sum);
        this.rentCreator = rentCreator;
        this.rentReturner = rentReturner;
    }

    @PreAuthorize("principal?.username == #username")
    @RequestMapping(value = "/rents", method = RequestMethod.POST)
    Map<String, Long> rent(@RequestParam Long filmId, @RequestParam Integer numberOfDays, @RequestParam String username) {
        Rent rent = rentCreator.rent(filmId, numberOfDays, username);
        return ImmutableMap.of("rentId", rent.getId());
    }

    @RequestMapping(value = "/returns", method = RequestMethod.POST)
    Map<String, BigDecimal> returnFilm(@RequestParam Long rentId) {
        Rent rent = rentReturner.returnFilm(rentId);
        return ImmutableMap.of("surcharge", rent.getLateReturnSurgcharge());
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Film not found")
    @ExceptionHandler(NoSuchFilmException.class)
    void statusForNoSuchFilm() {}

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Rent not found")
    @ExceptionHandler(NoSuchRentException.class)
    void statusForNoSuchRent() {}
}
