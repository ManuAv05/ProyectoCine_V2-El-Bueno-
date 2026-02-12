package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.cinema.SalaDTO;
import _DAM.Cine_V2.servicio.SalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salas")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService salaService;

    @GetMapping
    public ResponseEntity<List<SalaDTO>> findAll() {
        return ResponseEntity.ok(salaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(salaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SalaDTO> create(@Valid @RequestBody SalaDTO salaDTO) {
        return new ResponseEntity<>(salaService.save(salaDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDTO> update(@PathVariable Long id, @Valid @RequestBody SalaDTO salaDTO) {
        SalaDTO toUpdate = new SalaDTO(id, salaDTO.nombre(), salaDTO.capacidad());
        return ResponseEntity.ok(salaService.save(toUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        salaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
