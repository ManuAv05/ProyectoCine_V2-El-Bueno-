package _DAM.Cine_V2.servicio;

import _DAM.Cine_V2.dto.auth.LoginRequestDTO;
import _DAM.Cine_V2.dto.auth.LoginResponseDTO;
import _DAM.Cine_V2.dto.auth.RegisterRequestDTO;
import _DAM.Cine_V2.dto.usuario.UsuarioInputDTO;
import _DAM.Cine_V2.dto.usuario.UsuarioOutputDTO;
import _DAM.Cine_V2.mapper.UsuarioMapper;
import _DAM.Cine_V2.modelo.Rol;
import _DAM.Cine_V2.security.JwtService;
import _DAM.Cine_V2.modelo.Usuario;
import _DAM.Cine_V2.repositorio.RolRepository;
import _DAM.Cine_V2.repositorio.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder encoder; // Inyectado desde PasswordConfig
    private final JwtService jwtService;

    public List<UsuarioOutputDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioOutputDTO findById(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrada con ID: " + id));
    }

    @Transactional
    public UsuarioOutputDTO save(UsuarioInputDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);

        // Handle Roles
        if (usuarioDTO.roles() != null && !usuarioDTO.roles().isEmpty()) {
            Set<Rol> roles = new HashSet<>();
            for (String rolNombre : usuarioDTO.roles()) {
                Rol rol = rolRepository.findByNombre(rolNombre)
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolNombre));
                roles.add(rol);
            }
            usuario.setRoles(roles);
        }

        // 🔒 CIFRAR ANTES DE GUARDAR
        if (usuarioDTO.password() != null && !usuarioDTO.password().isBlank()) {
            usuario.setPassword(encoder.encode(usuarioDTO.password()));
        }

        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(saved);
    }

    @Transactional
    public UsuarioOutputDTO update(Long id, UsuarioInputDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrada con ID: " + id));

        usuarioMapper.update(usuarioDTO, usuario);

        // Handle Roles
        if (usuarioDTO.roles() != null) {
            Set<Rol> roles = new HashSet<>();
            for (String rolNombre : usuarioDTO.roles()) {
                Rol rol = rolRepository.findByNombre(rolNombre)
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolNombre));
                roles.add(rol);
            }
            usuario.setRoles(roles);
        }

        if (usuarioDTO.password() != null && !usuarioDTO.password().isBlank()) {
            usuario.setPassword(encoder.encode(usuarioDTO.password()));
        }

        return usuarioMapper.toDTO(usuarioRepository.save(usuario));
    }

    public void deleteById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    // ◆ REGISTRO
    @Transactional
    public void register(RegisterRequestDTO req) {
        Usuario u = new Usuario();
        u.setEmail(req.email());
        // 🔒 CIFRAR ANTES DE GUARDAR
        u.setPassword(encoder.encode(req.password()));
        u.setEnabled(true);
        usuarioRepository.save(u);
    }

    // ◆ LOGIN
    public LoginResponseDTO login(LoginRequestDTO req) {
        Usuario u = usuarioRepository.findByEmail(req.email())
                .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

        // 🔐 COMPARAR (Raw vs Hash)
        if (!encoder.matches(req.password(), u.getPassword())) {
            throw new BadCredentialsException("Credenciales incorrectas");
        }

        // Generamos el pase VIP (Token)
        String token = jwtService.generateToken(u);

        // Devolvemos DTO con todo
        return new LoginResponseDTO(
                u.getEmail(),
                "Login exitoso",
                token
        );
    }
}
