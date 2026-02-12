package _DAM.Cine_V2.dto.cinema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

public record VentaDTO(
                Long id,
                LocalDateTime fecha,
                double importeTotal,
                @NotBlank(message = "El método de pago es obligatorio") String metodoPago,
                String estado,
                Set<EntradaDTO> entradas,
                @NotNull(message = "El usuario es obligatorio") Long usuarioId) {
}
