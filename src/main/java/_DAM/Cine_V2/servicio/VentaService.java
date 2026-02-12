package _DAM.Cine_V2.servicio;

import _DAM.Cine_V2.dto.cinema.EntradaDTO;
import _DAM.Cine_V2.dto.cinema.VentaDTO;
import _DAM.Cine_V2.mapper.EntradaMapper;
import _DAM.Cine_V2.mapper.VentaMapper;
import _DAM.Cine_V2.modelo.*;
import _DAM.Cine_V2.repositorio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final UsuarioRepository usuarioRepository;
    private final FuncionRepository funcionRepository;
    // We don't necessarily need EntradaService if we implement logic here, but
    // using repository approach
    private final EntradaRepository entradaRepository;
    private final VentaMapper ventaMapper;
    private final EntradaMapper entradaMapper;

    public List<VentaDTO> findAll() {
        return ventaRepository.findAll().stream()
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public VentaDTO findById(Long id) {
        return ventaRepository.findById(id)
                .map(ventaMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
    }

    @Transactional
    public VentaDTO save(VentaDTO ventaDTO) {
        Venta venta = ventaMapper.toEntity(ventaDTO);

        if (ventaDTO.usuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(ventaDTO.usuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + ventaDTO.usuarioId()));
            venta.setUsuario(usuario);
        }

        // If we want to create tickets along with sale:
        if (ventaDTO.entradas() != null) {
            Set<Entrada> entradasEntities = new HashSet<>();
            for (EntradaDTO eDTO : ventaDTO.entradas()) {
                // Check function
                if (eDTO.funcionId() == null)
                    throw new RuntimeException("Entrada sin funcion ID");
                Funcion funcion = funcionRepository.findById(eDTO.funcionId())
                        .orElseThrow(() -> new RuntimeException("Funcion no encontrada " + eDTO.funcionId()));

                // Check availability (Naive check, assuming no concurrency issues for this
                // exercise)
                boolean occupied = entradaRepository.findByFuncionId(funcion.getId()).stream()
                        .anyMatch(e -> e.getFila() == eDTO.fila() && e.getAsiento() == eDTO.asiento()
                                && e.getEstado() != EstadoEntrada.CANCELADA);

                if (occupied) {
                    throw new RuntimeException("Asiento ocupado: " + eDTO.fila() + "-" + eDTO.asiento());
                }

                Entrada entrada = entradaMapper.toEntity(eDTO);
                entrada.setFuncion(funcion);
                entrada.setVenta(venta);
                if (entrada.getEstado() == null)
                    entrada.setEstado(EstadoEntrada.VENDIDA);
                entradasEntities.add(entrada);
            }
            venta.setEntradas(entradasEntities);
        }

        Venta saved = ventaRepository.save(venta);
        return ventaMapper.toDTO(saved);
    }

    public void deleteById(Long id) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada con ID: " + id);
        }
        ventaRepository.deleteById(id);
    }
}
