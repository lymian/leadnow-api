package com.leadnow.leads.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leadnow.leads.models.Formulario;

@Repository
public interface IFormularioRepository extends JpaRepository<Formulario, Long> {

    // listar formularios por usuario
    List<Formulario> findByUsuarioId(Long usuarioId);

    // Contar formularios por usuario
    Long countByUsuarioId(Long usuarioId);
}
