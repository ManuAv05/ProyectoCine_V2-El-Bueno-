package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.movie.DirectorDTO;
import _DAM.Cine_V2.modelo.Director;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DirectorMapper {
    DirectorDTO toDTO(Director director);

    Director toEntity(DirectorDTO directorDTO);
}
