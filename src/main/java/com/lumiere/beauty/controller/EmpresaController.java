package com.lumiere.beauty.controller;

import com.lumiere.beauty.entity.Empresa;
import com.lumiere.beauty.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @GetMapping("/empresas")
    public String verEmpresas(Model model) {
        List<Empresa> listaDeEmpresas = empresaRepository.findAll();
        // El nombre "lista" es el que usaremos en el HTML
        model.addAttribute("lista", listaDeEmpresas);
        return "ver_empresas"; 
    }
}