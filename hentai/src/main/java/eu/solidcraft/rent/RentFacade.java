package eu.solidcraft.rent;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RentFacade {
    ApplicationEventPublisher publisher;

    public void rent(String title, int numberOfDays) {
        //...
        publisher.publishEvent(new FilmWasRented(title));
    }

    @Value
    @AllArgsConstructor
    public static class FilmWasRented {
        String title;
    }
}

@Configuration
class RentFacadeConfiguration {
    @Bean
    RentFacade rentFacade(ApplicationEventPublisher publisher) {
        return new RentFacade(publisher);
    }
}
