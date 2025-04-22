package com.leadnow.leads.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FormularioMergeDTO {

    @NotBlank(message = "El campo nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El campo descripcion es obligatorio")
    private String descripcion;
}
