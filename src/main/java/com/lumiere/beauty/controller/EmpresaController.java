package com.lumiere.beauty.controller;

import com.lumiere.beauty.entity.*;
import com.lumiere.beauty.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class EmpresaController {

    @Autowired private EmpresaRepository empresaRepository;
    @Autowired private ServicioRepository servicioRepository;
    @Autowired private CitaRepository citaRepository;

    @GetMapping("/") public String index() { return "index"; }

    // --- CLIENTE ---
    @GetMapping("/explorar")
    public String explorar(Model model) {
        model.addAttribute("lista", empresaRepository.findAll());
        return "cliente_explorar";
    }

    @GetMapping("/empresa/perfil/{id}")
    public String perfilCliente(@PathVariable Long id, Model model) {
        model.addAttribute("empresa", empresaRepository.findById(id).get());
        model.addAttribute("servicios", servicioRepository.findByEmpresaId(id));
        return "cliente_perfil";
    }

    @GetMapping("/reservar/{empresaId}/{servicioId}")
    public String formularioReserva(@PathVariable Long empresaId, @PathVariable Long servicioId, Model model) {
        Cita cita = new Cita();
        cita.setEmpresa(empresaRepository.findById(empresaId).get());
        cita.setServicio(servicioRepository.findById(servicioId).get());
        model.addAttribute("cita", cita);
        return "cliente_reserva";
    }

    @PostMapping("/reservar/guardar")
    public String guardarCita(@ModelAttribute Cita cita, @RequestParam Long empresaId, @RequestParam Long servicioId) {
        cita.setEmpresa(empresaRepository.findById(empresaId).get());
        cita.setServicio(servicioRepository.findById(servicioId).get());
        citaRepository.save(cita);
        return "redirect:/explorar";
    }

    // --- EMPRENDEDOR ---
    @GetMapping("/empresas")
    public String verEmpresas(Model model) {
        model.addAttribute("lista", empresaRepository.findAll());
        return "ver_empresas";
    }

    @GetMapping("/empresas/gestionar/{id}")
    public String gestionar(@PathVariable Long id, Model model) {
        Empresa emp = empresaRepository.findById(id).get();
        model.addAttribute("empresa", emp);
        model.addAttribute("servicios", servicioRepository.findByEmpresaId(id));
        model.addAttribute("citas", citaRepository.findByEmpresaId(id)); // Enviamos las citas al dashboard
        model.addAttribute("nuevoServicio", new Servicio());
        return "empresa_dashboard";
    }

    @PostMapping("/servicios/guardar")
    public String guardarServicio(@ModelAttribute Servicio s, @RequestParam Long empresaId) {
        s.setEmpresa(empresaRepository.findById(empresaId).get());
        servicioRepository.save(s);
        return "redirect:/empresas/gestionar/" + empresaId;
    }
}