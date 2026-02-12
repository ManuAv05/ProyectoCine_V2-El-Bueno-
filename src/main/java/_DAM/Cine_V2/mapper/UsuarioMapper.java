package _DAM.Cine_V2.mapper;

import _DAM.Cine_V2.dto.auth.UsuarioDTO;
import _DAM.Cine_V2.modelo.Rol;
import _DAM.Cine_V2.modelo.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapper {

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRolesToStrings")
    @Mapping(target = "password", ignore = true) // Don't expose password in DTO by default or handle carefully
    UsuarioDTO toDTO(Usuario usuario);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "ventas", ignore = true)
    Usuario toEntity(UsuarioDTO usuarioDTO);

    @Named("mapRolesToStrings")
    default Set<String> mapRolesToStrings(Set<Rol> roles) {
        if (roles == null)
            return Collections.emptySet();
        return roles.stream().map(Rol::getNombre).collect(Collectors.toSet());
    }
}
