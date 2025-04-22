package com.leadnow.leads.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadnow.leads.dtos.FormularioDTO;
import com.leadnow.leads.dtos.FormularioMergeDTO;
import com.leadnow.leads.dtos.LeadDTO;
import com.leadnow.leads.models.Formulario;
import com.leadnow.leads.models.Lead;
import com.leadnow.leads.models.Usuario;
import com.leadnow.leads.repositories.IFormularioRepository;
import com.leadnow.leads.repositories.IUsuarioRepository;

@Service
public class FormularioService {

    @Autowired
    IFormularioRepository formularioRepository;
    @Autowired
    IUsuarioRepository usuarioRepository;

    public FormularioDTO saveFormulario(FormularioMergeDTO formularioMergeDTO) {
        Formulario formulario = convertirADto(formularioMergeDTO);
        Formulario savedFormulario = formularioRepository.save(formulario);
        return convertirADto(savedFormulario);
    }

    public FormularioDTO getFormularioById(Long id) {
        Formulario formulario = formularioRepository.findById(id).orElse(null);
        if (formulario != null) {
            return convertirADto(formulario);
        } else {
            return null;
        }
    }

    public int deleteFormulario(Long id) {
        Formulario formulario = formularioRepository.findById(id).orElse(null);
        if (formulario != null) {
            formularioRepository.delete(formulario);
            return 1; // éxito
        } else {
            return 0; // error
        }
    }

    public List<FormularioDTO> getAllFormularios() {
        List<Formulario> formularios = formularioRepository.findAll();
        return formularios.stream()
                .map(this::convertirADto)
                .toList();
    }

    public FormularioDTO crearFormulario(FormularioMergeDTO formularioMergeDTO, String username) {

        Formulario formulario = convertirADto(formularioMergeDTO);
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        formulario.setUsuario(usuario);
        formulario.setNombre(formularioMergeDTO.getNombre());
        formulario.setDescripcion(formularioMergeDTO.getDescripcion());
        // Setear la fecha actual
        formulario.setFechaCreacion(LocalDateTime.now());
        formulario.setDescripcion(formularioMergeDTO.getDescripcion());

        return convertirADto(formularioRepository.save(formulario));

    }

    // Listar formularios por usuario
    public List<FormularioDTO> getFormulariosByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if (usuario != null) {
            List<Formulario> formularios = formularioRepository.findByUsuarioId(usuario.getId());
            return formularios.stream()
                    .map(this::convertirADto)
                    .toList();
        } else {
            return null;
        }
    }

    // convertir FormularioMergeDTO a entidad Formulario
    private Formulario convertirADto(FormularioMergeDTO formularioMergeDTO) {
        Formulario formulario = new Formulario();
        formulario.setNombre(formularioMergeDTO.getNombre());
        formulario.setDescripcion(formularioMergeDTO.getDescripcion());

        return formulario;

    }

    // convertir Formulario a FormularioDTO
    private FormularioDTO convertirADto(Formulario formulario) {
        FormularioDTO formularioDTO = new FormularioDTO();
        formularioDTO.setId(formulario.getId());
        formularioDTO.setNombre(formulario.getNombre());
        formularioDTO.setDescripcion(formulario.getDescripcion());
        formularioDTO.setFechaCreacion(formulario.getFechaCreacion());
        formularioDTO.setUsuario(formulario.getUsuario().getUsername());
        // setear lista de leads
        List<Lead> leads = formulario.getLeads();
        if (leads != null) {
            List<LeadDTO> leadDTOs = leads.stream()
                    .map(this::convertirADto)
                    .toList();
            formularioDTO.setLeads(leadDTOs);
        } else {
            formularioDTO.setLeads(null);
        }
        return formularioDTO;
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
}