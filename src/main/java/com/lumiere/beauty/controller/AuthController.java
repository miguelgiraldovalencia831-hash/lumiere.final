package com.lumiere.beauty.controller;

import com.lumiere.beauty.entity.Usuario;
import com.lumiere.beauty.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth_registro";
    }

    @PostMapping("/registro/guardar")
    public String guardar(@ModelAttribute Usuario usuario, HttpSession session) {
        usuarioRepository.save(usuario);
        session.setAttribute("usuarioLogueado", usuario); // Inicia sesión automático
        if ("EMPRENDEDOR".equals(usuario.getRol())) {
            return "redirect:/empresas/nuevo";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() { return "auth_login"; }

    @PostMapping("/login/entrar")
    public String entrar(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Usuario user = usuarioRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("usuarioLogueado", user); // Guardar en sesión
            return "ADMIN".equals(user.getRol()) || "EMPRENDEDOR".equals(user.getRol()) ? "redirect:/empresas" : "redirect:/explorar";
        }
        return "redirect:/login?error";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Cerrar sesión
        return "redirect:/";
    }
}