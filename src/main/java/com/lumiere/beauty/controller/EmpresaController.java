package com.lumiere.beauty.controller;

import com.lumiere.beauty.entity.Empresa;
import com.lumiere.beauty.service.EmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public List<Empresa> listarTodas() {
        return empresaService.listarEmpresasActivas();
    }

    @GetMapping("/buscar")
    public List<Empresa> buscarPorNombre(@RequestParam String nombre) {
        return empresaService.buscarPorNombre(nombre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtenerPorId(@PathVariable Long id) {
        return EmpresaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}