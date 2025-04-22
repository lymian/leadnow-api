package com.leadnow.leads.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leadnow.leads.models.Lead;

@Repository
public interface ILeadRepository extends JpaRepository<Lead, Long> {

    //listar leads por formulario id
    List<Lead> findByFormularioId(Long formularioId);

}
