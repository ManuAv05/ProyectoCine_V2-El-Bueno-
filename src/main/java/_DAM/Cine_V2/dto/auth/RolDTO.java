package _DAM.Cine_V2.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RolDTO(
                Long id,
                @NotBlank(message = "El nombre del rol es obligatorio") String nombre) {
}
