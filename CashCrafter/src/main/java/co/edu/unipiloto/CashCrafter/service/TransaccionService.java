/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unipiloto.CashCrafter.service;
import co.edu.unipiloto.CashCrafter.dto.TransaccionDTO;
import co.edu.unipiloto.CashCrafter.entity.*;
import co.edu.unipiloto.CashCrafter.repository.TransaccionRepository;
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
public class TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public TransaccionDTO.TransaccionResponse registrarTransaccion(
            String username, TransaccionDTO.TransaccionRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        TransaccionFinanciera transaccion = new TransaccionFinanciera();
        transaccion.setUsuario(usuario);
        transaccion.setTipo(TipoTransaccion.valueOf(request.getTipo()));
        transaccion.setMonto(request.getMonto());
        transaccion.setDescripcion(request.getDescripcion());
        transaccion.setCategoria(request.getCategoria());
        transaccion.setFecha(request.getFecha() != null ? request.getFecha() : LocalDate.now());

        transaccion = transaccionRepository.save(transaccion);
        return convertirADTO(transaccion);
    }

    public List<TransaccionDTO.TransaccionResponse> obtenerTransaccionesUsuario(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return transaccionRepository.findByUsuarioOrderByFechaDesc(usuario)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public Map<String, Object> obtenerResumenFinanciero(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<TransaccionFinanciera> transacciones = transaccionRepository.findByUsuario(usuario);

        BigDecimal totalIngresos = transacciones.stream()
            .filter(t -> t.getTipo() == TipoTransaccion.INGRESO)
            .map(TransaccionFinanciera::getMonto)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalGastos = transacciones.stream()
            .filter(t -> t.getTipo() == TipoTransaccion.GASTO)
            .map(TransaccionFinanciera::getMonto)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Map.of(
            "totalIngresos", totalIngresos,
            "totalGastos", totalGastos,
            "balance", totalIngresos.subtract(totalGastos)
        );
    }

    private TransaccionDTO.TransaccionResponse convertirADTO(TransaccionFinanciera transaccion) {
        TransaccionDTO.TransaccionResponse dto = new TransaccionDTO.TransaccionResponse();
        dto.setId(transaccion.getId());
        dto.setTipo(transaccion.getTipo().toString());
        dto.setMonto(transaccion.getMonto());
        dto.setDescripcion(transaccion.getDescripcion());
        dto.setCategoria(transaccion.getCategoria());
        dto.setFecha(transaccion.getFecha());
        return dto;
    }
}
