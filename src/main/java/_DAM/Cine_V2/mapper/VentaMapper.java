package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.cinema.VentaDTO;
import _DAM.Cine_V2.modelo.Venta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { EntradaMapper.class })
public interface VentaMapper {

    @Mapping(target = "usuarioId", source = "usuario.id")
    VentaDTO toDTO(Venta venta);

    @Mapping(target = "usuario", ignore = true)
    Venta toEntity(VentaDTO ventaDTO);
}
