package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.cinema.VentaDTO;
import _DAM.Cine_V2.servicio.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaDTO>> findAll() {
        return ResponseEntity.ok(ventaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<VentaDTO> create(@Valid @RequestBody VentaDTO ventaDTO) {
        return new ResponseEntity<>(ventaService.save(ventaDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> update(@PathVariable Long id, @Valid @RequestBody VentaDTO ventaDTO) {
        VentaDTO toUpdate = new VentaDTO(id, ventaDTO.fecha(), ventaDTO.importeTotal(), ventaDTO.metodoPago(),
                ventaDTO.estado(), ventaDTO.entradas(), ventaDTO.usuarioId());
        return ResponseEntity.ok(ventaService.save(toUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ventaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
