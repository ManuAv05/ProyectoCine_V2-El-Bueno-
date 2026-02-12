package _DAM.Cine_V2.servicio;

import _DAM.Cine_V2.dto.movie.DirectorDTO;
import _DAM.Cine_V2.mapper.DirectorMapper;
import _DAM.Cine_V2.modelo.Director;
import _DAM.Cine_V2.repositorio.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;

    public List<DirectorDTO> findAll() {
        return directorRepository.findAll().stream()
                .map(directorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DirectorDTO findById(Long id) {
        return directorRepository.findById(id)
                .map(directorMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Director no encontrado con ID: " + id));
    }

    public DirectorDTO save(DirectorDTO directorDTO) {
        Director director = directorMapper.toEntity(directorDTO);
        Director saved = directorRepository.save(director);
        return directorMapper.toDTO(saved);
    }

    public void deleteById(Long id) {
        if (!directorRepository.existsById(id)) {
            throw new RuntimeException("Director no encontrado con ID: " + id);
        }
        directorRepository.deleteById(id);
    }
}
