package com.senai.backend.rental_eventos.repositories;

import com.senai.backend.rental_eventos.models.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer> {

    List<Movimentacao> findByEquipamentoIdOrderByDataMovimentacaoDesc(Integer equipamentoId);

    List<Movimentacao> findAllByOrderByDataMovimentacaoDesc();

}