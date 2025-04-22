package com.leadnow.leads.data;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.leadnow.leads.models.Formulario;
import com.leadnow.leads.models.Lead;
import com.leadnow.leads.models.Rol;
import com.leadnow.leads.models.Usuario;
import com.leadnow.leads.models.enums.EstadoLead;
import com.leadnow.leads.repositories.IFormularioRepository;
import com.leadnow.leads.repositories.ILeadRepository;
import com.leadnow.leads.repositories.IRolRepository;
import com.leadnow.leads.repositories.IUsuarioRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(
            IUsuarioRepository usuarioRepo,
            IFormularioRepository formularioRepo,
            ILeadRepository leadRepo,
            IRolRepository rolRepo,
            PasswordEncoder passwordEncoder) {
        return args -> {

            if (usuarioRepo.count() > 0) {
                System.out.println("La base de datos ya contiene datos. El DataLoader no se ejecutará.");
                return;
            }
            // Crear Rol
            Rol rol = new Rol();
            rol.setNombre("ROLE_ADMIN");
            rolRepo.save(rol);
            // Crear usuarios
            Usuario usuario1 = new Usuario();
            usuario1.setNombreCompleto("Juan Pérez");
            usuario1.setEmail("juan@example.com");
            usuario1.setUsername("admin");
            usuario1.setPassword(passwordEncoder.encode("123456"));
            usuario1.setRol(rol);

            Usuario usuario2 = new Usuario();
            usuario2.setNombreCompleto("Ana García");
            usuario2.setEmail("ana@example.com");
            usuario2.setUsername("anagarcia");
            usuario2.setPassword(passwordEncoder.encode("123456"));
            usuario2.setRol(rol);

            usuarioRepo.saveAll(List.of(usuario1, usuario2));

            // Crear formularios
            Formulario form1 = new Formulario();
            form1.setUsuario(usuario1);
            form1.setNombre("Formulario de contacto");
            form1.setDescripcion("Captura de clientes interesados");

            Formulario form2 = new Formulario();
            form2.setUsuario(usuario2);
            form2.setNombre("Landing de marketing");
            form2.setDescripcion("Formulario de campaña de Facebook");

            formularioRepo.saveAll(List.of(form1, form2));

            // Crear leads
            Lead lead1 = new Lead();
            lead1.setFormulario(form1);
            lead1.setNombre("Carlos Ramos");
            lead1.setEmail("carlos@mail.com");
            lead1.setEstado(EstadoLead.NUEVO);

            Lead lead2 = new Lead();
            lead2.setFormulario(form1);
            lead2.setNombre("Lucía Torres");
            lead2.setEmail("lucia@mail.com");
            lead2.setEstado(EstadoLead.ATENDIDO);

            Lead lead3 = new Lead();
            lead3.setFormulario(form2);
            lead3.setNombre("Pedro Sánchez");
            lead3.setEmail("pedro@mail.com");
            lead3.setEstado(EstadoLead.DESCARTADO);

            leadRepo.saveAll(Arrays.asList(lead1, lead2, lead3));

            System.out.println("✅ Datos iniciales cargados correctamente");
        };
    }
}