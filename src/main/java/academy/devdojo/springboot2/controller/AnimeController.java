package academy.devdojo.springboot2.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.service.AnimeService;
import academy.devdojo.springboot2.util.DateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Animes API")
@RequestMapping("/animes")
public class AnimeController {

	private final DateUtil dateUtil;
	private final AnimeService animeService;
	
	@GetMapping("/list")
	@Operation(description = "List of all animes")
	public ResponseEntity<List<Anime>> list() {
		log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
		return ResponseEntity.ok(animeService.listAll());
	}
	
	@GetMapping("/id/{id}")
	@Operation(description = "Find animes by id")
	public ResponseEntity<Anime> id(@PathVariable long id) {
		return ResponseEntity.ok(animeService.findById(id));
	}
	
	@GetMapping("/name/{name}")
	@Operation(description = "Find animes by name")
	public ResponseEntity<Anime> name(@PathVariable String name) {
		return ResponseEntity.ok(animeService.findByName(name));
	}
	
	@PostMapping("/add")
	@Operation(description = "Add animes")
	public ResponseEntity<Anime> save(@RequestBody Anime anime) {
		return new ResponseEntity<>(animeService.save(anime), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	@Operation(description = "Delete a anime by id")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		animeService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/put/{id}")
	@Operation(description = "Delete a anime by id")
	public ResponseEntity<Void> replace(@RequestBody Anime anime) {
		return new ResponseEntity<>(animeService.replace(anime), HttpStatus.ACCEPTED);
	}
}