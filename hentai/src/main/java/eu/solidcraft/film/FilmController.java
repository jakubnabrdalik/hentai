package eu.solidcraft.film;

import eu.solidcraft.film.domain.FilmFacade;
import eu.solidcraft.film.dto.FilmDto;
import eu.solidcraft.film.dto.FilmNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class FilmController {
    private FilmFacade filmFacade;

    public FilmController(FilmFacade filmFacade) {
        this.filmFacade = filmFacade;
    }

    @GetMapping("films")
    Page<FilmDto> getFilms(Pageable pageable) {
        return filmFacade.findAll(pageable);
    }

    @GetMapping("film/{title}")
    FilmDto getFilm(@PathVariable String title) {
        return filmFacade.show(title);
    }

    @ExceptionHandler(FilmNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handleNotFoundFilms() {
    }
}

