package eu.solidcraft.infrastructure.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AuthenticationConfiguration {
    @Bean
    CurrentUserGetter currentUserGetter() {
        return new CurrentUserGetter();
    }
}
