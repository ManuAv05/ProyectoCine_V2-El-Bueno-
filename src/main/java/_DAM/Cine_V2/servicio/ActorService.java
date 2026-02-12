package _DAM.Cine_V2.servicio;

import _DAM.Cine_V2.dto.movie.ActorDTO;
import _DAM.Cine_V2.mapper.ActorMapper;
import _DAM.Cine_V2.modelo.Actor;
import _DAM.Cine_V2.repositorio.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    public List<ActorDTO> findAll() {
        return actorRepository.findAll().stream()
                .map(actorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ActorDTO findById(Long id) {
        return actorRepository.findById(id)
                .map(actorMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Actor no encontrado con ID: " + id));
    }

    public ActorDTO save(ActorDTO actorDTO) {
        Actor actor = actorMapper.toEntity(actorDTO);
        Actor saved = actorRepository.save(actor);
        return actorMapper.toDTO(saved);
    }

    public void deleteById(Long id) {
        if (!actorRepository.existsById(id)) {
            throw new RuntimeException("Actor no encontrado con ID: " + id);
        }
        actorRepository.deleteById(id);
    }
}
