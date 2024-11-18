package co.edu.unipiloto.CashCrafter.service;

import co.edu.unipiloto.CashCrafter.dto.UsuarioDTO;
import co.edu.unipiloto.CashCrafter.entity.Usuario;
import co.edu.unipiloto.CashCrafter.entity.RolUsuario;
import co.edu.unipiloto.CashCrafter.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, 
                         PasswordEncoder passwordEncoder, 
                         EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public UsuarioDTO.UsuarioResponse registrarUsuario(UsuarioDTO.RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email ya registrado");
        }
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username ya registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setNombre(request.getNombre());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setUsername(request.getUsername());
        usuario.setRol(RolUsuario.USUARIO);
        usuario.setTokenVerificacion(UUID.randomUUID().toString());

        usuario = usuarioRepository.save(usuario);
        
        emailService.enviarEmailVerificacion(
            usuario.getEmail(),
            usuario.getTokenVerificacion()
        );

        return convertirADTO(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioDTO.UsuarioResponse obtenerUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirADTO(usuario);
    }

    @Transactional
    public UsuarioDTO.UsuarioResponse actualizarUsuario(Long id, UsuarioDTO.ActualizacionRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (request.getNombre() != null) {
            usuario.setNombre(request.getNombre());
        }
        if (request.getEmail() != null && !usuario.getEmail().equals(request.getEmail())) {
            if (usuarioRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email ya registrado");
            }
            usuario.setEmail(request.getEmail());
            usuario.setEmailVerificado(false);
            usuario.setTokenVerificacion(UUID.randomUUID().toString());
            emailService.enviarEmailVerificacion(request.getEmail(), usuario.getTokenVerificacion());
        }
        if (request.getPassword() != null) {
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        usuario = usuarioRepository.save(usuario);
        return convertirADTO(usuario);
    }

    @Transactional
    public void deshabilitarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    private UsuarioDTO.UsuarioResponse convertirADTO(Usuario usuario) {
        UsuarioDTO.UsuarioResponse dto = new UsuarioDTO.UsuarioResponse();
        dto.setId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setNombre(usuario.getNombre());
        dto.setUsername(usuario.getUsername());
        dto.setRol(usuario.getRol().toString());
        dto.setEmailVerificado(usuario.isEmailVerificado());
        dto.setActivo(usuario.isActivo());
        return dto;
    }

    @Transactional
    public void verificarEmail(String token) {
        Usuario usuario = usuarioRepository.findByTokenVerificacion(token)
            .orElseThrow(() -> new RuntimeException("Token inválido"));
        usuario.setEmailVerificado(true);
        usuario.setTokenVerificacion(null);
        usuarioRepository.save(usuario);
    }
}