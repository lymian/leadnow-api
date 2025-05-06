package com.leadnow.leads.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leadnow.leads.dtos.PasswordDTO;
import com.leadnow.leads.dtos.UsuarioDTO;
import com.leadnow.leads.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/administradores")
    @PreAuthorize("hasRole('ROLE_GERENTE')")
    public ResponseEntity<?> listarAdministradores() {
        return ResponseEntity.ok(usuarioService.getUsuariosAdministradires());
    }

    // Obtener usuario por username
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUsuarioByUsername(@PathVariable String username) {
        return ResponseEntity.ok(usuarioService.getUsuarioByUsername(username));
    }

    // Registrar trabajador
    @PreAuthorize("hasRole('ROLE_GERENTE')")
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarTrabajador(@Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(Map.of("errors", errors));
        }
        if (usuarioService.usernameExists(usuarioDTO.getUsername())) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya existe.");
        }
        if (usuarioService.emailExists(usuarioDTO.getEmail())) {
            return ResponseEntity.badRequest().body("El correo electrónico ya existe.");
        }
        return ResponseEntity.ok(usuarioService.registrarTrabajador(usuarioDTO));
    }

    // cambiar contraseña
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/cambiar-contrasena")
    public ResponseEntity<?> cambiarPassword(@Valid @RequestBody PasswordDTO password, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(Map.of("errors", errors));
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        try {
            usuarioService.actualizarContrasena(username, password.getNewPassword());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al cambiar la contraseña: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_GERENTE')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        int resultado = usuarioService.eliminarUsuarioPorId(id);
        if (resultado == 1) {
            return ResponseEntity.ok(Map.of("mensaje", "Usuario eliminado con éxito."));
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado.");
        }
    }
}