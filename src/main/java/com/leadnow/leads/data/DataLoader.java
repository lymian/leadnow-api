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

            Rol rol2 = new Rol();
            rol2.setNombre("ROLE_GERENTE");
            rolRepo.save(rol2);
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

            // Crear usuarios adicionales
            Usuario usuario3 = new Usuario();
            usuario3.setNombreCompleto("Luis Fernández");
            usuario3.setEmail("luis@example.com");
            usuario3.setUsername("luisfernandez");
            usuario3.setPassword(passwordEncoder.encode("123456"));
            usuario3.setRol(rol2);

            Usuario usuario4 = new Usuario();
            usuario4.setNombreCompleto("María López");
            usuario4.setEmail("marialopez@mail.com");
            usuario4.setUsername("marialopez");
            usuario4.setPassword(passwordEncoder.encode("123456"));
            usuario4.setRol(rol);

            usuarioRepo.saveAll(List.of(usuario3, usuario4));

            // crer 5 usuarios con rol admin
            Usuario usuario5 = new Usuario();
            usuario5.setNombreCompleto("Carlos Ruiz");
            usuario5.setEmail("carlos@mail.com");
            usuario5.setUsername("carlosruiz");
            usuario5.setPassword(passwordEncoder.encode("123456"));
            usuario5.setRol(rol);

            Usuario usuario6 = new Usuario();
            usuario6.setNombreCompleto("Lucía Torres");
            usuario6.setEmail("luci@mail.com");
            usuario6.setUsername("luciatorres");
            usuario6.setPassword(passwordEncoder.encode("123456"));
            usuario6.setRol(rol);

            Usuario usuario7 = new Usuario();
            usuario7.setNombreCompleto("Pedro Sánchez");
            usuario7.setEmail("pedro@mail.com");
            usuario7.setUsername("pedrosanchez");
            usuario7.setPassword(passwordEncoder.encode("123456"));
            usuario7.setRol(rol);

            Usuario usuario8 = new Usuario();
            usuario8.setNombreCompleto("Sofía Martínez");
            usuario8.setEmail("sofia@mail.com");
            usuario8.setUsername("sofia");
            usuario8.setPassword(passwordEncoder.encode("123456"));
            usuario8.setRol(rol);

            Usuario usuario9 = new Usuario();
            usuario9.setNombreCompleto("Andrés Gómez");
            usuario9.setEmail("andres@mail.com");
            usuario9.setUsername("andresgomez");
            usuario9.setPassword(passwordEncoder.encode("123456"));
            usuario9.setRol(rol);

            usuarioRepo.saveAll(List.of(usuario5, usuario6, usuario7, usuario8, usuario9));

            Formulario form1 = new Formulario();
            form1.setUsuario(usuario1);
            form1.setNombre("Formulario de contacto");
            form1.setDescripcion("Captura de clientes interesados");

            Formulario form2 = new Formulario();
            form2.setUsuario(usuario2);
            form2.setNombre("Landing de marketing");
            form2.setDescripcion("Formulario de campaña de Facebook");

            formularioRepo.saveAll(List.of(form1, form2));

            // Crear formularios solo para administradores

            Formulario form3 = new Formulario();
            form3.setUsuario(usuario4);
            form3.setNombre("Formulario de registro");
            form3.setDescripcion("Registro de usuarios en la plataforma");

            Formulario form4 = new Formulario();
            form4.setUsuario(usuario5);
            form4.setNombre("Formulario de encuesta");
            form4.setDescripcion("Encuesta de satisfacción del cliente");

            Formulario form5 = new Formulario();
            form5.setUsuario(usuario6);
            form5.setNombre("Formulario de feedback");
            form5.setDescripcion("Comentarios y sugerencias de los usuarios");

            Formulario form6 = new Formulario();
            form6.setUsuario(usuario7);
            form6.setNombre("Formulario de soporte");
            form6.setDescripcion("Soporte técnico y atención al cliente");

            Formulario form7 = new Formulario();
            form7.setUsuario(usuario8);
            form7.setNombre("Formulario de contacto");
            form7.setDescripcion("Formulario de contacto para clientes");

            Formulario form8 = new Formulario();
            form8.setUsuario(usuario9);
            form8.setNombre("Formulario de registro");
            form8.setDescripcion("Registro de usuarios en la plataforma");

            formularioRepo.saveAll(List.of(form3, form4, form5, form6, form7, form8));

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

            // Crear leads adicionales
            Lead lead4 = new Lead();
            lead4.setFormulario(form3);
            lead4.setNombre("Andrés Gómez");
            lead4.setEmail("gom@mail.com");
            lead4.setEstado(EstadoLead.NUEVO);

            Lead lead5 = new Lead();
            lead5.setFormulario(form4);
            lead5.setNombre("Sofía Martínez");
            lead5.setEmail("soff@mail.com");
            lead5.setEstado(EstadoLead.ATENDIDO);

            Lead lead6 = new Lead();
            lead6.setFormulario(form5);
            lead6.setNombre("Juan Pérez");
            lead6.setEmail("perj@mail.com");
            lead6.setEstado(EstadoLead.DESCARTADO);

            Lead lead7 = new Lead();
            lead7.setFormulario(form6);
            lead7.setNombre("María López");
            lead7.setEmail("marlop@mail.com");
            lead7.setEstado(EstadoLead.NUEVO);

            Lead lead8 = new Lead();
            lead8.setFormulario(form7);
            lead8.setNombre("Carlos Ruiz");
            lead8.setEmail("carly@mail.com");
            lead8.setEstado(EstadoLead.ATENDIDO);

            Lead lead9 = new Lead();
            lead9.setFormulario(form8);
            lead9.setNombre("Lucía Torres");
            lead9.setEmail("lutorr@mail.com");
            lead9.setEstado(EstadoLead.DESCARTADO);

            leadRepo.saveAll(Arrays.asList(lead4, lead5, lead6, lead7, lead8, lead9));

            System.out.println("✅ Datos iniciales cargados correctamente");
        };
    }
}