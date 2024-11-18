/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unipiloto.CashCrafter.service;
import co.edu.unipiloto.CashCrafter.dto.TransaccionDTO;
import co.edu.unipiloto.CashCrafter.dto.EncuestaDTO;
import co.edu.unipiloto.CashCrafter.entity.*;
import co.edu.unipiloto.CashCrafter.repository.TransaccionRepository;
import co.edu.unipiloto.CashCrafter.repository.EncuestaRepository;
import co.edu.unipiloto.CashCrafter.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.stream.Collectors;
/**
 *
 * @author maria
 */
@Service
public class EncuestaService {

    @Autowired
    private EncuestaRepository encuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public EncuestaDTO.EncuestaResponse registrarEncuesta(
            String username, EncuestaDTO.EncuestaRequest request) {
        if (request.getCalificacion() < 1 || request.getCalificacion() > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }

        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        EncuestaSatisfaccion encuesta = new EncuestaSatisfaccion();
        encuesta.setUsuario(usuario);
        encuesta.setCalificacion(request.getCalificacion());
        encuesta.setComentario(request.getComentario());
        encuesta.setFecha(LocalDate.now());

        encuesta = encuestaRepository.save(encuesta);
        return convertirADTO(encuesta);
    }

    private EncuestaDTO.EncuestaResponse convertirADTO(EncuestaSatisfaccion encuesta) {
        EncuestaDTO.EncuestaResponse dto = new EncuestaDTO.EncuestaResponse();
        dto.setId(encuesta.getId());
        dto.setCalificacion(encuesta.getCalificacion());
        dto.setComentario(encuesta.getComentario());
        dto.setFecha(encuesta.getFecha());
        return dto;
    }
}


