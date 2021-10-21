package academy.devdojo.springboot2.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import academy.devdojo.springboot2.service.AnimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Animes API")
@RequestMapping("/animes")
public class AnimeController {

	private final AnimeService animeService;
	
	@GetMapping
	@Operation(description = "List of all animes in pages")
	public ResponseEntity<Page<Anime>> list(Pageable pageable) {
		return ResponseEntity.ok(animeService.listAll(pageable));
	}
	
	@GetMapping("/all")
	@Operation(description = "List of all animes")
	public ResponseEntity<List<Anime>> listAll() {
		return ResponseEntity.ok(animeService.listAllNonPageable());
	}
	
	@GetMapping("/{id}")
	@Operation(description = "Find animes by id")
	public ResponseEntity<Anime> findById(@PathVariable long id) {
		return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
	}

	@GetMapping("/find")
	@Operation(description = "Find animes by name")
	public ResponseEntity<List<Anime>> findByName(@RequestParam String name) {
		return ResponseEntity.ok(animeService.findByName(name));
	}
	
	@PostMapping
	@Operation(description = "Add animes")
	public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody) {
		return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	@Operation(description = "Delete a anime by id")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		animeService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/replace")
	@Operation(description = "Delete a anime by id")
	public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody) {
		animeService.replace(animePutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}