/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unipiloto.CashCrafter.controller;
import co.edu.unipiloto.CashCrafter.dto.TransaccionDTO;
import co.edu.unipiloto.CashCrafter.dto.EncuestaDTO;
import co.edu.unipiloto.CashCrafter.service.TransaccionService;
import co.edu.unipiloto.CashCrafter.service.EncuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author maria
 */
@RestController
@RequestMapping("/api/finanzas")
public class FinanzasController {

    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private EncuestaService encuestaService;

    @PostMapping("/transacciones")
    public ResponseEntity<TransaccionDTO.TransaccionResponse> registrarTransaccion(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody TransaccionDTO.TransaccionRequest request) {
        return ResponseEntity.ok(transaccionService.registrarTransaccion(userDetails.getUsername(), request));
    }

    @GetMapping("/transacciones")
    public ResponseEntity<?> obtenerTransacciones(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(transaccionService.obtenerTransaccionesUsuario(userDetails.getUsername()));
    }

    @PostMapping("/encuesta")
    public ResponseEntity<EncuestaDTO.EncuestaResponse> registrarEncuesta(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody EncuestaDTO.EncuestaRequest request) {
        return ResponseEntity.ok(encuestaService.registrarEncuesta(userDetails.getUsername(), request));
    }

    @GetMapping("/resumen")
    public ResponseEntity<?> obtenerResumenFinanciero(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(transaccionService.obtenerResumenFinanciero(userDetails.getUsername()));
    }
}