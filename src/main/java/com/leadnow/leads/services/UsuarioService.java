package com.leadnow.leads.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.leadnow.leads.dtos.TrabajadorDTO;
import com.leadnow.leads.dtos.UsuarioDTO;
import com.leadnow.leads.models.Rol;
import com.leadnow.leads.models.Usuario;
import com.leadnow.leads.repositories.IRolRepository;
import com.leadnow.leads.repositories.IUsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    // Registrar trabajador
    public TrabajadorDTO registrarTrabajador(UsuarioDTO usuarioDTO) {

        Rol rol = rolRepository.findByNombre("ROLE_ADMIN");

        Usuario usuario = new Usuario();
        usuario.setNombreCompleto(usuarioDTO.getNombreCompleto());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setRol(rol);
        usuario.setPassword(passwordEncoder.encode("123456"));

        return convertToTrabajadorDTO(usuarioRepository.save(usuario));
    }

    // username existente
    public boolean usernameExists(String username) {
        return usuarioRepository.findByUsername(username).isPresent();
    }

    // email existente
    public boolean emailExists(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    // Actualizar contraseña por username
    public void actualizarContrasena(String username, String nuevaContrasena) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if (usuario != null) {
            usuario.setPassword(passwordEncoder.encode(nuevaContrasena));
            usuarioRepository.save(usuario);
        }
    }

    // Eliminar usuario por id
    public int eliminarUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuarioRepository.delete(usuario);
            return 1; // éxito
        }
        return 0; // error
    }

    // convertir Usuario a TrabajadoresDTO
    public TrabajadorDTO convertToTrabajadorDTO(Usuario usuario) {
        TrabajadorDTO trabajadorDTO = new TrabajadorDTO();
        trabajadorDTO.setId(usuario.getId());
        trabajadorDTO.setNombreCompleto(usuario.getNombreCompleto());
        trabajadorDTO.setEmail(usuario.getEmail());
        trabajadorDTO.setUsername(usuario.getUsername());
        trabajadorDTO.setFechaRegistro(usuario.getFechaRegistro().toString());
        // si get formularios es null, setear a 0
        if (usuario.getFormularios() == null) {
            trabajadorDTO.setNumeroFormularios(0);
            return trabajadorDTO;
        }
        trabajadorDTO.setNumeroFormularios(usuario.getFormularios().size());
        return trabajadorDTO;
    }

}
