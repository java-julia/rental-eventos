package com.senai.backend.rental_eventos.controllers;

import com.senai.backend.rental_eventos.models.Movimentacao;
import com.senai.backend.rental_eventos.services.MovimentacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movimentacoes")
@CrossOrigin(origins = "*")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(
            MovimentacaoService movimentacaoService
    ) {
        this.movimentacaoService = movimentacaoService;
    }

    @PostMapping
    public ResponseEntity<Movimentacao> realizarMovimentacao(
            @RequestParam Integer equipamentoId,
            @RequestParam Integer usuarioId,
            @RequestParam String tipoMovimentacao,
            @RequestParam Integer quantidade,
            @RequestParam(required = false) LocalDateTime dataMovimentacao,
            @RequestParam(required = false) String observacao
    ) {

        Movimentacao movimentacao =
                movimentacaoService.realizarMovimentacao(
                        equipamentoId,
                        usuarioId,
                        tipoMovimentacao,
                        quantidade,
                        dataMovimentacao,
                        observacao
                );

        return ResponseEntity.ok(movimentacao);
    }

    @GetMapping
    public ResponseEntity<List<Movimentacao>> listarTodas() {

        return ResponseEntity.ok(
                movimentacaoService.listarTodas()
        );
    }

    @GetMapping("/equipamento/{equipamentoId}")
    public ResponseEntity<List<Movimentacao>> listarPorEquipamento(
            @PathVariable Integer equipamentoId
    ) {

        return ResponseEntity.ok(
                movimentacaoService.listarPorEquipamento(
                        equipamentoId
                )
        );
    }

    @GetMapping("/estoque/{equipamentoId}")
    public ResponseEntity<String> verificarEstoque(
            @PathVariable Integer equipamentoId
    ) {

        return ResponseEntity.ok(
                movimentacaoService.verificarEstoque(
                        equipamentoId
                )
        );
    }
}