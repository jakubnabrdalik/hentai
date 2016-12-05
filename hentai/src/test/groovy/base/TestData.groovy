package base
import eu.solidcraft.hentai.films.Film
import eu.solidcraft.hentai.films.FilmType

class TestData {
    static Film film0 = new Film(filmType: FilmType.NEW_RELEASE, title: "Pirate Ninja Tentacle attacks", description: "Classic Japan family movie")
    static Film film1 = new Film(filmType: FilmType.REGULAR, title: "Day of the Tentacle", description: "LucasArts penetrates Japan market with huuuge impact!")
    static Film film2 = new Film(filmType: FilmType.OLD, title: "It came in the deep", description: "Enigma tentacle grabs its market")
    static ArrayList<Film> films = [film0, film1, film2]
}
