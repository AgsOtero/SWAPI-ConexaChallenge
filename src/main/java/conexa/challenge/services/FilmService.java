package conexa.challenge.services;

import conexa.challenge.model.FilmDTO;

import java.util.List;

public interface FilmService {

    List<FilmDTO> getAllFilms();
    FilmDTO getFilmById(int id);
    FilmDTO getFilmByTitle(String title);
}
