package academy.devdojo.springboot2.integration;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.util.AnimeCreator;
import academy.devdojo.springboot2.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2.util.AnimePutRequestBodyCreator;
import academy.devdojo.springboot2.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
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

//	@Test
//    @DisplayName("findById returns anime when successful")
//    void findById_ReturnsAnime_WhenSuccessful(){
//        Long expectedId = AnimeCreator.createValidAnime().getId();
//
//        Anime anime = animeController.findById(1).getBody();
//
//        Assertions.assertThat(anime).isNotNull();
//
//        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
//    }
//	
//	@Test
//    @DisplayName("findByName returns list of anime when successful")
//    void findByName_ReturnsListOfAnime_WhenSuccessful(){
//		String expectName = AnimeCreator.createValidAnime().getName();
//		List<Anime> animes = animeController.findByName("anime").getBody();
//		
//		Assertions.assertThat(animes)
//				.isNotNull()
//				.isNotEmpty()
//				.hasSize(1);
//		
//		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectName);
//    }
//	
//	@Test
//    @DisplayName("findByName returns an empty list of anime when anime is not found")
//    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound(){
//		BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
//        .thenReturn(Collections.emptyList());
//		
//		List<Anime> animes = animeController.findByName("anime").getBody();
//		
//		Assertions.assertThat(animes)
//				.isNotNull()
//				.isEmpty();
//		
//    }
//	
//	@Test
//    @DisplayName("save returns anime when successful")
//    void save_ReturnsAnime_WhenSuccessful(){
//
//        Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();
//
//        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
//    }
//	
//	@Test
//    @DisplayName("replace updates anime when successful")
//    void replace_UpdatesAnime_WhenSuccessful(){
//
//		Assertions.assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
//				.doesNotThrowAnyException();
//		
//		ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());
//        
//        Assertions.assertThat(entity).isNotNull();
//        
//        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//
//    }
//	
//	@Test
//    @DisplayName("delete removes anime when successful")
//    void delete_RemovesAnime_WhenSuccessful(){
//
//		Assertions.assertThatCode(() -> animeController.delete(1L))
//				.doesNotThrowAnyException();
//		
//		ResponseEntity<Void> entity = animeController.delete(1L);
//        
//        Assertions.assertThat(entity).isNotNull();
//        
//        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//
//    }
}
