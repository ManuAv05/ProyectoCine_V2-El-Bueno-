package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.movie.DirectorDTO;
import _DAM.Cine_V2.servicio.DirectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/directores")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping
    public ResponseEntity<List<DirectorDTO>> findAll() {
        return ResponseEntity.ok(directorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(directorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DirectorDTO> create(@Valid @RequestBody DirectorDTO directorDTO) {
        return new ResponseEntity<>(directorService.save(directorDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectorDTO> update(@PathVariable Long id, @Valid @RequestBody DirectorDTO directorDTO) {
        // Ensure ID matches
        if (directorDTO.id() != null && !directorDTO.id().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        // If record, we need to create a new one with ID if it's missing or trust
        // service updates it?
        // Service.save usually updates if ID exists.
        // But record is immutable. We pass the dto as is.
        // It's better if we reconstruct the DTO with the ID to ensure correctness or
        // rely on Service handling.
        // Here I'll assume client sends ID in body or I ignore path ID vs body ID
        // mismatch?
        // Standard practice: force path ID.
        // DirectorDTO is a record.
        DirectorDTO toUpdate = new DirectorDTO(id, directorDTO.nombre());
        return ResponseEntity.ok(directorService.save(toUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        directorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
