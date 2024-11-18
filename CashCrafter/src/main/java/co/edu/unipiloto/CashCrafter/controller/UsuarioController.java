/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unipiloto.CashCrafter.controller;

import co.edu.unipiloto.CashCrafter.dto.UsuarioDTO;
import co.edu.unipiloto.CashCrafter.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author maria
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO.UsuarioResponse> registrarUsuario(
            @RequestBody UsuarioDTO.RegistroRequest request) {
        return ResponseEntity.ok(usuarioService.registrarUsuario(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO.UsuarioResponse> obtenerUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuario(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO.UsuarioResponse> actualizarUsuario(
            @PathVariable Long id,
            @RequestBody UsuarioDTO.ActualizacionRequest request) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deshabilitarUsuario(@PathVariable Long id) {
        usuarioService.deshabilitarUsuario(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verificar")
    public ResponseEntity<Void> verificarEmail(@RequestParam String token) {
        usuarioService.verificarEmail(token);
        return ResponseEntity.ok().build();
    }
}