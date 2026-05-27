package com.lumiere.beauty.controller;

import com.lumiere.beauty.entity.Empresa;
import com.lumiere.beauty.entity.Servicio;
import com.lumiere.beauty.repository.EmpresaRepository;
import com.lumiere.beauty.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ServicioRepository ServicioRepository;

    // --- PÁGINAS GENERALES ---
    @GetMapping("/")
    public String index() { return "index"; }

    @GetMapping("/explorar")
    public String explorar(Model model) {
        model.addAttribute("lista", empresaRepository.findAll());
        return "cliente_explorar";
    }

    @GetMapping("/empresa/perfil/{id}")
    public String perfilEmpresa(@PathVariable Long id, Model model) {
        Empresa emp = empresaRepository.findById(id).orElse(null);
        if (emp != null) {
            model.addAttribute("empresa", emp);
            model.addAttribute("servicios", ServicioRepository.findByEmpresaId(id));
            return "cliente_perfil";
        }
        return "redirect:/explorar";
    }

    // --- GESTIÓN DE EMPRENDEDOR ---
    @GetMapping("/empresas")
    public String verEmpresas(Model model) {
        model.addAttribute("lista", empresaRepository.findAll());
        return "ver_empresas";
    }

    @GetMapping("/empresas/nuevo")
    public String formularioNueva(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresa_formulario";
    }

    @PostMapping("/empresas/guardar")
    public String guardar(@ModelAttribute Empresa empresa) {
        empresaRepository.save(empresa);
        return "redirect:/empresas";
    }

    @GetMapping("/empresas/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        empresaRepository.deleteById(id);
        return "redirect:/empresas";
    }

    @GetMapping("/empresas/gestionar/{id}")
    public String gestionar(@PathVariable Long id, Model model) {
        Empresa emp = empresaRepository.findById(id).orElse(null);
        if (emp != null) {
            model.addAttribute("empresa", emp);
            model.addAttribute("servicios", ServicioRepository.findByEmpresaId(id));
            model.addAttribute("nuevoServicio", new Servicio());
            return "empresa_dashboard";
        }
        return "redirect:/empresas";
    }

    @PostMapping("/servicios/guardar")
    public String guardarServicio(@ModelAttribute Servicio servicio, @RequestParam("empresaId") Long empresaId) {
        Empresa emp = empresaRepository.findById(empresaId).orElse(null);
        if (emp != null) {
            servicio.setEmpresa(emp);
            ServicioRepository.save(servicio);
        }
        return "redirect:/empresas/gestionar/" + empresaId;
    }
}