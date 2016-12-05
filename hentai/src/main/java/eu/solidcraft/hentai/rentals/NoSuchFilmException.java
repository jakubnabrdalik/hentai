package eu.solidcraft.hentai.rentals;


class NoSuchFilmException extends RuntimeException {
    private Long filmId;

    NoSuchFilmException(Long filmId) {
        this.filmId = filmId;
    }
}
