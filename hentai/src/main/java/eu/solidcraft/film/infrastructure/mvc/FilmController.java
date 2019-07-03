package eu.solidcraft.film.infrastructure.mvc;

import eu.solidcraft.film.FilmFacade;
import eu.solidcraft.film.dto.FilmDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class FilmController {
    FilmFacade filmFacade;

    @GetMapping("/film")
    Page<FilmDto> findFilms(Pageable pageable) {
        return filmFacade.findFilms(pageable);
    }



}
