package academy.devdojo.springboot2.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.util.AnimeCreator;
import academy.devdojo.springboot2.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AnimeControllerIT {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private AnimeRepository animeRepository;
	
	@Test
	@DisplayName("list returns list of anime inside page object succesful")
	void list_ReturnsListOfAnimesInsidePageObject_WhenSuccesful() {
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		String expectName = savedAnime.getName();
		
		PageableResponse<Anime> animePage = testRestTemplate.exchange("/animes", HttpMethod.GET, null,
				new ParameterizedTypeReference<PageableResponse<Anime>>(){
		}).getBody();
		
		Assertions.assertThat(animePage).isNotNull();
		Assertions.assertThat(animePage.toList())
			.isNotEmpty()
			.hasSize(1);
		Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectName);
	}
	
	@Test
	@DisplayName("listAll returns list of animes when succesful")
	void listAll_ReturnsListOfAnimes_WhenSuccesful() {
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		String expectName = savedAnime.getName();
		
		List<Anime> animes = testRestTemplate.exchange("/animes/all", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Anime>>(){
		}).getBody();
		
		Assertions.assertThat(animes)
			.isNotNull()
			.isNotEmpty()
			.hasSize(1);
		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectName);
	}

	@Test
    @DisplayName("findById returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful(){
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
        Long expectedId = savedAnime.getId();

        Anime anime = testRestTemplate.getForObject("/animes/{id}", Anime.class, expectedId);

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }
	
	@Test
    @DisplayName("findByName returns list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
		animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		String expectName = AnimeCreator.createValidAnime().getName();
		String url = String.format("/animes/find?name=%s", expectName);
		List<Anime> animes = testRestTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Anime>>(){
		}).getBody();
		
		Assertions.assertThat(animes)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);
		
		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectName);
    }
	
	@Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound(){
		List<Anime> animes = testRestTemplate.exchange("/animes/find?name=dbz", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Anime>>(){
		}).getBody();
		
		Assertions.assertThat(animes)
				.isNotNull()
				.isEmpty();
		
    }
	
	@Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){
		AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
        ResponseEntity<Anime> animeResponseEntity = testRestTemplate.postForEntity("/animes", animePostRequestBody, Anime.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
    }
	
	@Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful(){
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		savedAnime.setName("Boku no Hero");
		
        ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/animes/replace",
        		HttpMethod.PUT,
        		new HttpEntity<>(savedAnime),
        		Void.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
	
	@Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
        ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/animes/delete/{id}",
        		HttpMethod.DELETE,
        		null,
        		Void.class,
        		savedAnime.getId()
        );

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}
