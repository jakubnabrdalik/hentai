package eu.solidcraft.hentai.films;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Film {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private FilmType filmType;

    private String title;

    private String description;

    public Long getId() {
        return id;
    }

    public FilmType getFilmType() {
        return filmType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
