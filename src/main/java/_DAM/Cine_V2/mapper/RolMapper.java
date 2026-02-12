package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.auth.RolDTO;
import _DAM.Cine_V2.modelo.Rol;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RolMapper {
    RolDTO toDTO(Rol rol);

    Rol toEntity(RolDTO rolDTO);
}
