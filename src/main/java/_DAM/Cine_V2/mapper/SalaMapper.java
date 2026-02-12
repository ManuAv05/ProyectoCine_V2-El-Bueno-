package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.cinema.SalaDTO;
import _DAM.Cine_V2.modelo.Sala;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SalaMapper {
    SalaDTO toDTO(Sala sala);

    Sala toEntity(SalaDTO salaDTO);
}
