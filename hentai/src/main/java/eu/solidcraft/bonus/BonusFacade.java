package eu.solidcraft.bonus;

import eu.solidcraft.infrastructure.config.AppProfiles;
import eu.solidcraft.rent.RentFacade.FilmWasRented;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BonusFacade {

    @EventListener
    @Async("bonusPointsTaskExecutor")
    public void calculatePoints(FilmWasRented  filmWasRented) {
        String name = Thread.currentThread().getName();
        System.out.println(name + " " + filmWasRented);
    }
}

@EnableAsync
@Configuration
class BonusConfiguration {
    @Bean
    BonusFacade bonusFacade() {
        return new BonusFacade();
    }

    @Profile(value = {"!" + AppProfiles.INTEGRATION})
    @Bean
    TaskExecutor bonusPointsTaskExecutor() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setThreadGroupName("bonusPointsTaskExecutor");
        threadPoolTaskScheduler.setPoolSize(30);
        return threadPoolTaskScheduler;
    }

    @Profile(value = {AppProfiles.INTEGRATION})
    @Bean("bonusPointsTaskExecutor")
    TaskExecutor integrationTaskExecutor() {
        return (Runnable task) -> task.run();
    }
}
