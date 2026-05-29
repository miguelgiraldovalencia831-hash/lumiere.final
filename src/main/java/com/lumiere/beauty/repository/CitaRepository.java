package com.lumiere.beauty.repository;

import com.lumiere.beauty.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    // Busca las citas filtradas por el ID de la empresa
    List<Cita> findByEmpresaId(Long empresaId);
}