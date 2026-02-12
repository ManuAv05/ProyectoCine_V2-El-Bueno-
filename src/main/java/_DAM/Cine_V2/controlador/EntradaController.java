package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.cinema.EntradaDTO;
import _DAM.Cine_V2.servicio.EntradaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entradas")
@RequiredArgsConstructor
public class EntradaController {

    private final EntradaService entradaService;

    @GetMapping
    public ResponseEntity<List<EntradaDTO>> findAll() {
        return ResponseEntity.ok(entradaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(entradaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EntradaDTO> create(@Valid @RequestBody EntradaDTO entradaDTO) {
        return new ResponseEntity<>(entradaService.save(entradaDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntradaDTO> update(@PathVariable Long id, @Valid @RequestBody EntradaDTO entradaDTO) {
        EntradaDTO toUpdate = new EntradaDTO(id, entradaDTO.codigo(), entradaDTO.fila(), entradaDTO.asiento(),
                entradaDTO.estado(), entradaDTO.funcionId(), entradaDTO.ventaId());
        return ResponseEntity.ok(entradaService.save(toUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        entradaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
