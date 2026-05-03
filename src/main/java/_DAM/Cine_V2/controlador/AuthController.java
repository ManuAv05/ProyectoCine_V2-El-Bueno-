package _DAM.Cine_V2.controlador;

import _DAM.Cine_V2.dto.auth.LoginRequestDTO;
import _DAM.Cine_V2.dto.auth.LoginResponseDTO;
import _DAM.Cine_V2.dto.auth.RegisterRequestDTO;
import _DAM.Cine_V2.dto.auth.RegisterResponseDTO;
import _DAM.Cine_V2.servicio.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService service;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO req) {
        service.register(req);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegisterResponseDTO(req.email()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO req) {
        try {
            return ResponseEntity.ok(service.login(req));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
