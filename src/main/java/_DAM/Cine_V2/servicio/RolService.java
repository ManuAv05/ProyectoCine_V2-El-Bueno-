package _DAM.Cine_V2.servicio;

import _DAM.Cine_V2.dto.auth.RolDTO;
import _DAM.Cine_V2.mapper.RolMapper;
import _DAM.Cine_V2.modelo.Rol;
import _DAM.Cine_V2.repositorio.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    public List<RolDTO> findAll() {
        return rolRepository.findAll().stream()
                .map(rolMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RolDTO findById(Long id) {
        return rolRepository.findById(id)
                .map(rolMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
    }

    public RolDTO save(RolDTO rolDTO) {
        Rol rol = rolMapper.toEntity(rolDTO);
        Rol saved = rolRepository.save(rol);
        return rolMapper.toDTO(saved);
    }

    public void deleteById(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado con ID: " + id);
        }
        rolRepository.deleteById(id);
    }
}
