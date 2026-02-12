package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.movie.ActorDTO;
import _DAM.Cine_V2.servicio.ActorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/actores")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @GetMapping
    public ResponseEntity<List<ActorDTO>> findAll() {
        return ResponseEntity.ok(actorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(actorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ActorDTO> create(@Valid @RequestBody ActorDTO actorDTO) {
        return new ResponseEntity<>(actorService.save(actorDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActorDTO> update(@PathVariable Long id, @Valid @RequestBody ActorDTO actorDTO) {
        ActorDTO toUpdate = new ActorDTO(id, actorDTO.nombre());
        return ResponseEntity.ok(actorService.save(toUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        actorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
