package com.lumiere.beauty.controller;

import com.lumiere.beauty.entity.*;
import com.lumiere.beauty.repository.*;
import jakarta.servlet.http.HttpSession;
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

    // --- EXPLORAR CON FILTRO ---
    @GetMapping("/explorar")
    public String explorar(Model model, @RequestParam(required = false) String buscar) {
        List<Empresa> lista;
        if (buscar != null && !buscar.isEmpty()) {
            lista = empresaRepository.findByNombreContainingIgnoreCaseOrDireccionContainingIgnoreCase(buscar, buscar);
        } else {
            lista = empresaRepository.findAll();
        }
        model.addAttribute("lista", lista);
        return "cliente_explorar";
    }

    @GetMapping("/empresas")
    public String verEmpresas(Model model, HttpSession session) {
        Usuario loginUser = (Usuario) session.getAttribute("usuarioLogueado");
        if (loginUser == null) return "redirect:/login";

        // SI ES ADMIN VE TODO, SI NO, SOLO LO SUYO
        if ("ADMIN".equals(loginUser.getRol())) {
            model.addAttribute("lista", empresaRepository.findAll());
        } else {
            model.addAttribute("lista", empresaRepository.findByUsuarioId(loginUser.getId()));
        }
        return "ver_empresas";
    }

    @GetMapping("/empresas/nuevo")
    public String formularioNueva(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresa_formulario";
    }

    @PostMapping("/empresas/guardar")
    public String guardar(@ModelAttribute Empresa empresa, HttpSession session) {
        Usuario loginUser = (Usuario) session.getAttribute("usuarioLogueado");
        if (loginUser != null) {
            empresa.setUsuario(loginUser);
            empresaRepository.save(empresa);
        }
        return "redirect:/empresas";
    }

    // El resto de métodos (gestionar, eliminar, perfil) se mantienen igual...
    @GetMapping("/empresas/gestionar/{id}")
    public String gestionar(@PathVariable Long id, Model model) {
        Empresa emp = empresaRepository.findById(id).orElse(null);
        model.addAttribute("empresa", emp);
        model.addAttribute("servicios", servicioRepository.findByEmpresaId(id));
        model.addAttribute("citas", citaRepository.findByEmpresaId(id));
        model.addAttribute("nuevoServicio", new Servicio());
        return "empresa_dashboard";
    }

    @PostMapping("/servicios/guardar")
    public String guardarServicio(@ModelAttribute Servicio servicio, @RequestParam("empresaId") Long empresaId) {
        Empresa emp = empresaRepository.findById(empresaId).orElse(null);
        servicio.setEmpresa(emp);
        servicioRepository.save(servicio);
        return "redirect:/empresas/gestionar/" + empresaId;
    }

    @GetMapping("/empresa/perfil/{id}")
    public String perfilCliente(@PathVariable Long id, Model model) {
        model.addAttribute("empresa", empresaRepository.findById(id).get());
        model.addAttribute("servicios", servicioRepository.findByEmpresaId(id));
        return "cliente_perfil";
    }
}