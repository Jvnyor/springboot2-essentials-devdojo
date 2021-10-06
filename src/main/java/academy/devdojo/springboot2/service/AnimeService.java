package academy.devdojo.springboot2.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import academy.devdojo.springboot2.domain.Anime;

@Service
public class AnimeService {
//  private final AnimeRepository animeRepository;
	private List<Anime> animes = List.of(new Anime(1L,"DBZ"), new Anime(2L,"Berserk"));
	
	public List<Anime> listAll() {
		return animes;
	}
	
	public Anime findById(long id) {
        return animes.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not Found"));
    }
	
	public Anime findByName(String name) {
		return animes.stream()
                .filter(anime -> anime.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not Found"));
	}
}