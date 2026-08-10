package com.senai.backend.rental_eventos.repositories;

import com.senai.backend.rental_eventos.models.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer> {

    List<Equipamento> findByNomeContainingIgnoreCase(String nome);

    List<Equipamento> findAllByOrderByNomeAsc();

}