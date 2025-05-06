package com.leadnow.leads.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leadnow.leads.models.Rol;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Long> {
    Rol findByNombre(String nombre);
}