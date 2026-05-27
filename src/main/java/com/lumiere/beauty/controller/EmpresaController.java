package com.lumiere.beauty.controller;

import com.lumiere.beauty.entity.*;
import com.lumiere.beauty.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmpresaController {

    @Autowired private EmpresaRepository empresaRepository;
    @Autowired private ServicioRepository servicioRepository;

    @GetMapping("/") public String index() { return "index"; }

    @GetMapping("/empresas")
    public String verEmpresas(Model model) {
        model.addAttribute("lista", empresaRepository.findAll());
        return "ver_empresas";
    }

    @GetMapping("/empresas/nuevo")
    public String formulario(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresa_formulario";
    }

    @PostMapping("/empresas/guardar")
    public String guardar(@ModelAttribute Empresa e) {
        empresaRepository.save(e);
        return "redirect:/empresas";
    }

    @GetMapping("/empresas/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        empresaRepository.deleteById(id);
        return "redirect:/empresas";
    }

    @GetMapping("/empresas/gestionar/{id}")
    public String gestionar(@PathVariable Long id, Model model) {
        Empresa emp = empresaRepository.findById(id).get();
        model.addAttribute("empresa", emp);
        model.addAttribute("servicios", servicioRepository.findByEmpresaId(id));
        model.addAttribute("nuevoServicio", new Servicio());
        return "empresa_dashboard";
    }

    @PostMapping("/servicios/guardar")
    public String guardarServicio(@ModelAttribute Servicio s, @RequestParam Long empresaId) {
        Empresa emp = empresaRepository.findById(empresaId).get();
        s.setEmpresa(emp);
        servicioRepository.save(s);
        return "redirect:/empresas/gestionar/" + empresaId;
    }

    @GetMapping("/explorar")
    public String explorar(Model model) {
        model.addAttribute("lista", empresaRepository.findAll());
        return "cliente_explorar";
    }

    @GetMapping("/empresa/perfil/{id}")
    public String perfil(@PathVariable Long id, Model model) {
        model.addAttribute("empresa", empresaRepository.findById(id).get());
        model.addAttribute("servicios", servicioRepository.findByEmpresaId(id));
        return "cliente_perfil";
    }
}