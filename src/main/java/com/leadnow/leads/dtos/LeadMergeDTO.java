package com.leadnow.leads.dtos;

import com.leadnow.leads.models.enums.EstadoLead;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LeadMergeDTO {

    @NotBlank(message = "El campo nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El campo email es obligatorio")
    private String email;

    @NotNull(message = "El campo estado es obligatorio")
    private EstadoLead estado;

    @NotNull(message = "El campo idFormulario es obligatorio")
    private Long idFormulario;
}
