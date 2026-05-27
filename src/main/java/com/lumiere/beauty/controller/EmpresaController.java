package com.lumiere.beauty.controller;

import com.lumiere.beauty.entity.Empresa;
import com.lumiere.beauty.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    // PÁGINA DE INICIO (Selección de Rol)
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // LISTADO DE EMPRESAS (Vista Emprendedor)
    @GetMapping("/empresas")
    public String verEmpresas(Model model) {
        List<Empresa> lista = empresaRepository.findAll();
        model.addAttribute("lista", lista);
        return "ver_empresas";
    }

    // FORMULARIO PARA NUEVA EMPRESA
    @GetMapping("/empresas/nuevo")
    public String formularioNuevaEmpresa(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresa_formulario";
    }

    // GUARDAR EMPRESA EN LA BASE DE DATOS
    @PostMapping("/empresas/guardar")
    public String guardarEmpresa(@ModelAttribute Empresa empresa) {
        empresaRepository.save(empresa);
        return "redirect:/empresas";
    }
}