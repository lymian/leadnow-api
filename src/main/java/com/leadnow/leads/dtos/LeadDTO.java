package com.leadnow.leads.dtos;

import java.time.LocalDateTime;

import com.leadnow.leads.models.enums.EstadoLead;

import lombok.Data;

@Data
public class LeadDTO {
    private Long id;
    private String nombre;
    private String email;
    private LocalDateTime fechaEnvio;
    private EstadoLead estado;
}