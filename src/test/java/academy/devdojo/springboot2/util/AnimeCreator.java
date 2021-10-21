package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.domain.Anime;

public class AnimeCreator {

	public static Anime createAnimeToBeSaved() {
		return Anime.builder()
				.name("Hajime no Ippo")
				.build();
	}
	
	public static Anime createValidAnime() {
		return Anime.builder()		
				.name("Hajime no Ippo")
				.id(1L)
				.build();
	}
	
	public static Anime createValidUpdatedAnine() {
		return Anime.builder()
				.name("Hajime no Ippo")
				.id(1L)
				.build();
	}
	
}
