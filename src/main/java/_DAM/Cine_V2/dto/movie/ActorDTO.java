package _DAM.Cine_V2.dto.movie;

import jakarta.validation.constraints.NotBlank;

public record ActorDTO(
                Long id,
                @NotBlank(message = "El nombre no puede estar vacío") String nombre) {
}
