package co.edu.unipiloto.CashCrafter.repository;

import co.edu.unipiloto.CashCrafter.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
/**
 *
 * @author maria
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Usuario> findByTokenVerificacion(String token);
}