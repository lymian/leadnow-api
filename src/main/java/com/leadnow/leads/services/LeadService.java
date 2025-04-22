package com.leadnow.leads.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadnow.leads.dtos.LeadDTO;
import com.leadnow.leads.dtos.LeadMergeDTO;
import com.leadnow.leads.models.Lead;
import com.leadnow.leads.repositories.IFormularioRepository;
import com.leadnow.leads.repositories.ILeadRepository;

@Service
public class LeadService {

    @Autowired
    private ILeadRepository leadRepository;
    @Autowired
    private IFormularioRepository formularioRepository;

    public LeadDTO saveLead(LeadMergeDTO lead) {
        Lead leadEntity = convertirAEntidad(lead);
        leadEntity.setFechaEnvio(LocalDateTime.now());
        leadEntity = leadRepository.save(leadEntity);
        return convertirADto(leadEntity);
    }

    public LeadDTO updateLead(Long id, LeadMergeDTO lead) {
        Lead leadEntity = leadRepository.findById(id).orElse(null);
        if (leadEntity != null) {
            leadEntity.setNombre(lead.getNombre());
            leadEntity.setEmail(lead.getEmail());
            leadEntity.setEstado(lead.getEstado());
            leadEntity = leadRepository.save(leadEntity);
            return convertirADto(leadEntity);
        } else {
            return null; // O lanzar una excepción si no se encuentra el lead
        }
    }

    public LeadDTO getLeadById(Long id) {
        Lead lead = leadRepository.findById(id).orElse(null);
        if (lead != null) {
            return convertirADto(lead);
        } else {
            return null; // O lanzar una excepción si no se encuentra el lead
        }
    }

    public int deleteLead(Long id) {
        if (leadRepository.existsById(id)) {
            leadRepository.deleteById(id);
            return 1; // Lead eliminado con éxito
        } else {
            return 0; // Lead no encontrado
        }
    }

    public List<LeadDTO> getAllLeads() {
        List<Lead> leads = leadRepository.findAll();
        return leads.stream().map(this::convertirADto).toList();
    }

    public List<LeadDTO> getLeadsByFormularioId(Long formularioId) {
        List<Lead> leads = leadRepository.findByFormularioId(formularioId);
        return leads.stream().map(this::convertirADto).toList();
    }

    // Convertir Lead a LeadDTO
    private LeadDTO convertirADto(Lead lead) {
        LeadDTO leadDTO = new LeadDTO();
        leadDTO.setId(lead.getId());
        leadDTO.setNombre(lead.getNombre());
        leadDTO.setEmail(lead.getEmail());
        leadDTO.setFechaEnvio(lead.getFechaEnvio());
        leadDTO.setEstado(lead.getEstado());
        return leadDTO;
    }

    public Lead convertirAEntidad(LeadMergeDTO leadMergeDTO) {
        Lead lead = new Lead();
        lead.setNombre(leadMergeDTO.getNombre());
        lead.setEmail(leadMergeDTO.getEmail());
        lead.setEstado(leadMergeDTO.getEstado());
        lead.setFormulario(formularioRepository.findById(leadMergeDTO.getIdFormulario()).orElse(null));
        return lead;
    }
}