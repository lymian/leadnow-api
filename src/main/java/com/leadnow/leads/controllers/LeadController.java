package com.leadnow.leads.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leadnow.leads.dtos.LeadMergeDTO;
import com.leadnow.leads.services.LeadService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/leads")
public class LeadController {

    @Autowired
    private LeadService leadService;

    @GetMapping("/listar")
    public ResponseEntity<?> listarLeads() {
        return ResponseEntity.ok(leadService.getAllLeads());
    }

    // obtener pasando id
    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> obtenerLead(@PathVariable Long id) {
        return ResponseEntity.ok(leadService.getLeadById(id));
    }

    // obtener pasando id formulario
    @GetMapping("/obtener/formulario/{idFormulario}")
    public ResponseEntity<?> obtenerLeadsPorFormulario(@PathVariable Long idFormulario) {
        return ResponseEntity.ok(leadService.getLeadsByFormularioId(idFormulario));
    }

    // eliminar pasando id
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarLead(@PathVariable Long id) {
        int resultado = leadService.deleteLead(id);
        if (resultado == 1) {
            return ResponseEntity.ok("Lead eliminado con éxito.");
        } else {
            return ResponseEntity.status(404).body("Lead no encontrado.");
        }
    }

    // Crear lead
    @PostMapping("/crear")
    public ResponseEntity<?> crearLead(@RequestBody LeadMergeDTO lead) {
        return ResponseEntity.ok(leadService.saveLead(lead));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarLead(@PathVariable Long id, @Valid @RequestBody LeadMergeDTO lead,
            BindingResult result) {

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(Map.of("errors", errors));
        }
        return ResponseEntity.ok(leadService.updateLead(id, lead));
    }
}
