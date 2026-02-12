package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.movie.PeliculaDTO;
import _DAM.Cine_V2.servicio.PeliculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/peliculas")
@RequiredArgsConstructor
public class PeliculaController {

    private final PeliculaService peliculaService;

    @GetMapping
    public ResponseEntity<List<PeliculaDTO>> findAll() {
        return ResponseEntity.ok(peliculaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeliculaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(peliculaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PeliculaDTO> create(@Valid @RequestBody PeliculaDTO peliculaDTO) {
        return new ResponseEntity<>(peliculaService.save(peliculaDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeliculaDTO> update(@PathVariable Long id, @Valid @RequestBody PeliculaDTO peliculaDTO) {
        PeliculaDTO toUpdate = new PeliculaDTO(id, peliculaDTO.titulo(), peliculaDTO.duracion(),
                peliculaDTO.edadMinima(), peliculaDTO.directorId(), peliculaDTO.actorIds());
        return ResponseEntity.ok(peliculaService.save(toUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        peliculaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
