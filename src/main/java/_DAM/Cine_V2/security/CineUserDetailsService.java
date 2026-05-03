package _DAM.Cine_V2.security;

import _DAM.Cine_V2.modelo.Rol;
import _DAM.Cine_V2.modelo.Usuario;
import _DAM.Cine_V2.repositorio.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CineUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1. Buscamos el usuario en NUESTRA base de datos
        Usuario u = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        // 2. LO TRADUCIMOS al formato que Spring Security entiende
        // ⚠️ CORRECCIÓN: Las slides usan u.getRol() (String), pero nuestro modelo
        // tiene Set<Rol> con @ManyToMany. Extraemos los nombres con stream.
        String[] roles = u.getRoles().stream()
                .map(Rol::getNombre)
                .toArray(String[]::new);

        // Si no tiene roles asignados, ponemos USER por defecto
        if (roles.length == 0) {
            roles = new String[]{"USER"};
        }

        return User.builder()
                .username(u.getEmail())
                .password(u.getPassword()) // La contraseña cifrada
                .roles(roles)              // Asigna los permisos
                .build();
    }
}
