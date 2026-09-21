package com.samuca.lanchehub.repository;

import com.samuca.lanchehub.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
    Optional<Mesa> findByQrToken(String qrToken);
}
