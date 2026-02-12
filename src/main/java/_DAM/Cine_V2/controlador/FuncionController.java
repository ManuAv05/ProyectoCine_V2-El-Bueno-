package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.cinema.FuncionDTO;
import _DAM.Cine_V2.servicio.FuncionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/funciones")
@RequiredArgsConstructor
public class FuncionController {

    private final FuncionService funcionService;

    @GetMapping
    public ResponseEntity<List<FuncionDTO>> findAll() {
        return ResponseEntity.ok(funcionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(funcionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FuncionDTO> create(@Valid @RequestBody FuncionDTO funcionDTO) {
        return new ResponseEntity<>(funcionService.save(funcionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionDTO> update(@PathVariable Long id, @Valid @RequestBody FuncionDTO funcionDTO) {
        FuncionDTO toUpdate = new FuncionDTO(id, funcionDTO.fechaHora(), funcionDTO.precio(), funcionDTO.peliculaId(),
                funcionDTO.salaId());
        return ResponseEntity.ok(funcionService.save(toUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        funcionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
