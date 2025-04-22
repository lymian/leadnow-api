package com.leadnow.leads.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class FormularioDTO {

    private Long id;

    private String usuario;

    private String nombre;

    private String descripcion;

    private LocalDateTime fechaCreacion;

    private List<LeadDTO> leads;
}