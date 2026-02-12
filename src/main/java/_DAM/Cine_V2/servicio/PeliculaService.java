package _DAM.Cine_V2.servicio;

import _DAM.Cine_V2.dto.movie.PeliculaDTO;
import _DAM.Cine_V2.mapper.PeliculaMapper;
import _DAM.Cine_V2.modelo.Actor;
import _DAM.Cine_V2.modelo.Director;
import _DAM.Cine_V2.modelo.Pelicula;
import _DAM.Cine_V2.repositorio.ActorRepository;
import _DAM.Cine_V2.repositorio.DirectorRepository;
import _DAM.Cine_V2.repositorio.PeliculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final PeliculaMapper peliculaMapper;

    @Transactional(readOnly = true)
    public List<PeliculaDTO> findAll() {
        return peliculaRepository.findAll().stream()
                .map(peliculaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PeliculaDTO findById(Long id) {
        return peliculaRepository.findById(id)
                .map(peliculaMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Pelicula no encontrada con ID: " + id));
    }

    @Transactional
    public PeliculaDTO save(PeliculaDTO peliculaDTO) {
        Pelicula pelicula = peliculaMapper.toEntity(peliculaDTO);

        if (peliculaDTO.directorId() != null) {
            Director director = directorRepository.findById(peliculaDTO.directorId())
                    .orElseThrow(
                            () -> new RuntimeException("Director no encontrado con ID: " + peliculaDTO.directorId()));
            pelicula.setDirector(director);
        }

        if (peliculaDTO.actorIds() != null && !peliculaDTO.actorIds().isEmpty()) {
            List<Actor> actores = actorRepository.findAllById(peliculaDTO.actorIds());
            if (actores.size() != peliculaDTO.actorIds().size()) {
                throw new RuntimeException("Algunos actores no fueron encontrados");
            }
            pelicula.setActores(new HashSet<>(actores));
        }

        Pelicula saved = peliculaRepository.save(pelicula);
        return peliculaMapper.toDTO(saved);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!peliculaRepository.existsById(id)) {
            throw new RuntimeException("Pelicula no encontrada con ID: " + id);
        }
        peliculaRepository.deleteById(id);
    }
}
