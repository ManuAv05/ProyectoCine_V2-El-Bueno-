package _DAM.Cine_V2.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public record UsuarioDTO(
                Long id,
                @Email(message = "El formato del email no es válido") @NotBlank(message = "El email no puede estar vacío") String email,
                String password,
                boolean enabled,
                Set<String> roles) {
}
