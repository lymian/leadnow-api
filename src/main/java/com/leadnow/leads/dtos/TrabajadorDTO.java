package com.leadnow.leads.dtos;

import lombok.Data;

@Data
public class TrabajadorDTO {
    private Long id;
    private String nombreCompleto;
    private String email;
    private String username;
    // fecha de registro
    private String fechaRegistro;
    private int numeroFormularios;
}
