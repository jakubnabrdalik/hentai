package eu.solidcraft.film.domain;

import eu.solidcraft.film.dto.FilmDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
class Film {
    @Id
    private String title;
    private FilmType type;

    FilmDto dto() {
        return FilmDto.builder()
                .title(title)
                .type(type.dto())
                .build();
    }
}
