package _DAM.Cine_V2.servicio;

import _DAM.Cine_V2.dto.cinema.EntradaDTO;
import _DAM.Cine_V2.mapper.EntradaMapper;
import _DAM.Cine_V2.modelo.Entrada;
import _DAM.Cine_V2.modelo.EstadoEntrada;
import _DAM.Cine_V2.modelo.Funcion;
import _DAM.Cine_V2.modelo.Venta;
import _DAM.Cine_V2.repositorio.EntradaRepository;
import _DAM.Cine_V2.repositorio.FuncionRepository;
import _DAM.Cine_V2.repositorio.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntradaService {

    private final EntradaRepository entradaRepository;
    private final FuncionRepository funcionRepository;
    private final VentaRepository ventaRepository;
    private final EntradaMapper entradaMapper;

    @Transactional(readOnly = true)
    public List<EntradaDTO> findAll() {
        return entradaRepository.findAll().stream()
                .map(entradaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EntradaDTO findById(Long id) {
        return entradaRepository.findById(id)
                .map(entradaMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Entrada no encontrada con ID: " + id));
    }

    @Transactional
    public EntradaDTO save(EntradaDTO entradaDTO) {
        // Validation: Seat availability
        if (isSeatOccupied(entradaDTO.funcionId(), entradaDTO.fila(), entradaDTO.asiento())) {
            throw new RuntimeException(
                    "El asiento " + entradaDTO.fila() + "-" + entradaDTO.asiento() + " ya está ocupado.");
        }

        Entrada entrada = entradaMapper.toEntity(entradaDTO);

        if (entradaDTO.funcionId() != null) {
            Funcion funcion = funcionRepository.findById(entradaDTO.funcionId())
                    .orElseThrow(() -> new RuntimeException("Funcion no encontrada con ID: " + entradaDTO.funcionId()));
            entrada.setFuncion(funcion);
        }

        if (entradaDTO.ventaId() != null) {
            Venta venta = ventaRepository.findById(entradaDTO.ventaId())
                    .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + entradaDTO.ventaId()));
            entrada.setVenta(venta);
        }

        // Default status if not provided
        if (entrada.getEstado() == null) {
            entrada.setEstado(EstadoEntrada.VENDIDA);
        }

        Entrada saved = entradaRepository.save(entrada);
        return entradaMapper.toDTO(saved);
    }

    public boolean isSeatOccupied(Long funcionId, int fila, int asiento) {
        // This is a naive implementation. In a real system, we'd have a specific query.
        // Or we check existing tickets for this function and seat.
        List<Entrada> entradas = entradaRepository.findByFuncionId(funcionId);
        return entradas.stream().anyMatch(
                e -> e.getFila() == fila && e.getAsiento() == asiento && e.getEstado() != EstadoEntrada.CANCELADA);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!entradaRepository.existsById(id)) {
            throw new RuntimeException("Entrada no encontrada con ID: " + id);
        }
        entradaRepository.deleteById(id);
    }
}
