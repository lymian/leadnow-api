package com.leadnow.leads.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadnow.leads.dtos.TrabajadorDTO;
import com.leadnow.leads.models.Usuario;
import com.leadnow.leads.repositories.IUsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public void saveUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<TrabajadorDTO> getUsuariosAdministradires() {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> usuario.getRol().getNombre().equals("ROLE_ADMIN"))
                .map(this::convertToTrabajadorDTO)
                .toList();
    }

    // obtener por username
    public TrabajadorDTO getUsuarioByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if (usuario != null) {
            return convertToTrabajadorDTO(usuario);
        }
        return null;
    }

    // convertir Usuario a TrabajadoresDTO
    public TrabajadorDTO convertToTrabajadorDTO(Usuario usuario) {
        TrabajadorDTO trabajadorDTO = new TrabajadorDTO();
        trabajadorDTO.setId(usuario.getId());
        trabajadorDTO.setNombreCompleto(usuario.getNombreCompleto());
        trabajadorDTO.setEmail(usuario.getEmail());
        trabajadorDTO.setUsername(usuario.getUsername());
        trabajadorDTO.setFechaRegistro(usuario.getFechaRegistro().toString());
        trabajadorDTO.setNumeroFormularios(usuario.getFormularios().size());
        return trabajadorDTO;
    }
}
