package _DAM.Cine_V2.dto.movie;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record PeliculaDTO(
                Long id,
                @NotBlank(message = "El título es obligatorio") String titulo,
                @Min(value = 1, message = "La duración debe ser mayor a 0") int duracion,
                @Min(value = 0, message = "La edad mínima no puede ser negativa") int edadMinima,
                @NotNull(message = "El director es obligatorio") Long directorId,
                Set<Long> actorIds) {
}
