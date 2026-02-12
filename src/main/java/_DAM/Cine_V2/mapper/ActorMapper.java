package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.movie.ActorDTO;
import _DAM.Cine_V2.modelo.Actor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ActorMapper {
    ActorDTO toDTO(Actor actor);

    Actor toEntity(ActorDTO actorDTO);
}
