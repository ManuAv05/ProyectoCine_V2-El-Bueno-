package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.cinema.EntradaDTO;
import _DAM.Cine_V2.modelo.Entrada;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EntradaMapper {

    @Mapping(target = "funcionId", source = "funcion.id")
    @Mapping(target = "ventaId", source = "venta.id")
    EntradaDTO toDTO(Entrada entrada);

    @Mapping(target = "funcion", ignore = true)
    @Mapping(target = "venta", ignore = true)
    Entrada toEntity(EntradaDTO entradaDTO);
}
