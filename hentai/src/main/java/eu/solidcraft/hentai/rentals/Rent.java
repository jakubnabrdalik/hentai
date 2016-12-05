package eu.solidcraft.hentai.rentals;

import eu.solidcraft.hentai.films.FilmType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Entity
public class Rent {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private Long filmId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate rentDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueBy;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate returnedOn;

    private BigDecimal priceCalculated;

    private BigDecimal lateReturnSurgcharge = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private FilmType filmType;

    Rent() {}

    //a bit long, could be extracted to a builder with a nice fluid interface
    Rent(Long filmId, FilmType filmType, Integer numberOfDays, String username, LocalDate rentDate, RentPriceCalculator rentPriceCalculator) {
        this.filmType = filmType;
        this.username = username;
        this.filmId = filmId;
        this.rentDate = rentDate;
        this.dueBy = rentDate.plus(numberOfDays, ChronoUnit.DAYS);
        this.priceCalculated = rentPriceCalculator.calculatePrice(filmType, numberOfDays);
    }

    public void returned(LocalDate returnedOn, RentPriceCalculator rentPriceCalculator) {
        this.returnedOn = returnedOn;
        if(returnedOn.isAfter(dueBy)) {
            int numberOfDaysRented = Period.between(rentDate, returnedOn).getDays();
            BigDecimal chargeShouldBe = rentPriceCalculator.calculatePrice(filmType, numberOfDaysRented);
            lateReturnSurgcharge = chargeShouldBe.subtract(priceCalculated);
        }
    }

    //required by Spring Data Rest MVC

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Long getFilmId() {
        return filmId;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public LocalDate getDueBy() {
        return dueBy;
    }

    public LocalDate getReturnedOn() {
        return returnedOn;
    }

    public BigDecimal getPriceCalculated() {
        return priceCalculated;
    }

    public BigDecimal getLateReturnSurgcharge() {
        return lateReturnSurgcharge;
    }
}
