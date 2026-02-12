package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.cinema.FuncionDTO;
import _DAM.Cine_V2.modelo.Funcion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FuncionMapper {

    @Mapping(target = "peliculaId", source = "pelicula.id")
    @Mapping(target = "salaId", source = "sala.id")
    FuncionDTO toDTO(Funcion funcion);

    @Mapping(target = "pelicula", ignore = true)
    @Mapping(target = "sala", ignore = true)
    @Mapping(target = "entradas", ignore = true)
    Funcion toEntity(FuncionDTO funcionDTO);
}
