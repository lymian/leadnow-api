package com.leadnow.leads.controllers;

import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;

import com.leadnow.leads.dtos.FormularioMergeDTO;
import com.leadnow.leads.services.FormularioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/formularios")
public class FormularioController {

    @Autowired
    private FormularioService formularioService;

    @GetMapping("/listar")
    public ResponseEntity<?> getAllFormularios() {
        try {
            return ResponseEntity.ok(formularioService.getAllFormularios());
        } catch (Exception e) {
            return ResponseEntity.status(Response.SC_INTERNAL_SERVER_ERROR)
                    .body("Error retrieving formularios: " + e.getMessage());
        }
    }

    @GetMapping("obtener/{id}")
    public ResponseEntity<?> getFormularioById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(formularioService.getFormularioById(id));
        } catch (Exception e) {
            return ResponseEntity.status(Response.SC_INTERNAL_SERVER_ERROR)
                    .body("Error retrieving formulario: " + e.getMessage());
        }
    }

    // obtener formularios por usuario autenticado
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mis-formularios")
    public ResponseEntity<?> getFormulariosByUsuarioId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        try {
            return ResponseEntity.ok(formularioService.getFormulariosByUsername(username));
        } catch (Exception e) {
            return ResponseEntity.status(Response.SC_INTERNAL_SERVER_ERROR)
                    .body("Error retrieving formularios: " + e.getMessage());
        }
    }

    // Crear formulario
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/crear")
    public ResponseEntity<?> crearFormulario(@Valid @RequestBody FormularioMergeDTO formularioMergeDTO,
            BindingResult result) {
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
            return ResponseEntity.ok(formularioService.crearFormulario(formularioMergeDTO, username));
        } catch (Exception e) {
            return ResponseEntity.status(Response.SC_INTERNAL_SERVER_ERROR)
                    .body("Error creating formulario: " + e.getMessage());
        }
    }

    // eliminar formulario
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarFormulario(@PathVariable Long id) {
        try {
            int resultado = formularioService.deleteFormulario(id);
            if (resultado == 1) {
                return ResponseEntity.ok(Map.of("mensaje", "Formulario eliminado con éxito."));
            } else {
                return ResponseEntity.status(404).body("Formulario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(Response.SC_INTERNAL_SERVER_ERROR)
                    .body("Error deleting formulario: " + e.getMessage());
        }
    }

    // actualizar formulario
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarFormulario(@PathVariable Long id,
            @Valid @RequestBody FormularioMergeDTO formularioMergeDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(Map.of("errors", errors));
        }
        try {
            return ResponseEntity.ok(formularioService.updateFormulario(id, formularioMergeDTO));
        } catch (Exception e) {
            return ResponseEntity.status(Response.SC_INTERNAL_SERVER_ERROR)
                    .body("Error updating formulario: " + e.getMessage());
        }
    }

    // contar formularios por id usuario
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/contar/{idUsuario}")
    public ResponseEntity<?> contarFormulariosPorUsuario(@PathVariable Long idUsuario) {
        try {
            return ResponseEntity.ok(formularioService.countFormulariosByUsuarioId(idUsuario));
        } catch (Exception e) {
            return ResponseEntity.status(Response.SC_INTERNAL_SERVER_ERROR)
                    .body("Error counting formularios: " + e.getMessage());
        }
    }

    // obtener formularios por id usuario
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/obtener/usuario/{idUsuario}")
    public ResponseEntity<?> getFormulariosByUsuarioId(@PathVariable Long idUsuario) {
        try {
            return ResponseEntity.ok(formularioService.getFormulariosByUsuarioId(idUsuario));
        } catch (Exception e) {
            return ResponseEntity.status(Response.SC_INTERNAL_SERVER_ERROR)
                    .body("Error retrieving formularios: " + e.getMessage());
        }
    }
}