package com.lumiere.beauty.repository;

import com.lumiere.beauty.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    
    // Filtro para que el emprendedor solo vea sus negocios
    List<Empresa> findByUsuarioId(Long usuarioId);

    // Filtro de búsqueda por nombre o dirección (ignora mayúsculas/minúsculas)
    List<Empresa> findByNombreContainingIgnoreCaseOrDireccionContainingIgnoreCase(String nombre, String direccion);
}