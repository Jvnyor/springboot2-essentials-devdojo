package academy.devdojo.springboot2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import academy.devdojo.springboot2.domain.Anime;

@Service
public class AnimeService {
//  private final AnimeRepository animeRepository;
	private static List<Anime> animes;
	static {
		animes = new ArrayList<>(List.of(new Anime(1L,"DBZ"), new Anime(2L,"Berserk")));
	}
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

	public Anime save(Anime anime) {
//		for (int i = animes.size(); i==animes.size(); i+=1) {
//			anime.setId(Long.valueOf(i));
//		}
		anime.setId(ThreadLocalRandom.current().nextLong(3,1000));
		animes.add(anime);
		return anime;
	}

	public void delete(Long id) {
		animes.remove(findById(id));
	}

	public void replace(Anime anime) {
		delete(anime.getId());
		animes.add(anime);
	}
	
	
}