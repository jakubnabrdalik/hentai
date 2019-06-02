package eu.solidcraft.films.infrastructure.mvc;

import eu.solidcraft.films.FilmFacade;
import eu.solidcraft.films.dto.FilmDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/film/{title}")
    ResponseEntity<FilmDto> findDetails(@PathVariable String title) {
        return filmFacade.findOne(title)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
