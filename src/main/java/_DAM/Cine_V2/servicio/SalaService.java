package _DAM.Cine_V2.servicio;

import _DAM.Cine_V2.dto.cinema.SalaDTO;
import _DAM.Cine_V2.mapper.SalaMapper;
import _DAM.Cine_V2.modelo.Sala;
import _DAM.Cine_V2.repositorio.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository salaRepository;
    private final SalaMapper salaMapper;

    public List<SalaDTO> findAll() {
        return salaRepository.findAll().stream()
                .map(salaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SalaDTO findById(Long id) {
        return salaRepository.findById(id)
                .map(salaMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada con ID: " + id));
    }

    public SalaDTO save(SalaDTO salaDTO) {
        Sala sala = salaMapper.toEntity(salaDTO);
        Sala saved = salaRepository.save(sala);
        return salaMapper.toDTO(saved);
    }

    public void deleteById(Long id) {
        if (!salaRepository.existsById(id)) {
            throw new RuntimeException("Sala no encontrada con ID: " + id);
        }
        salaRepository.deleteById(id);
    }
}
