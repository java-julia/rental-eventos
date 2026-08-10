package com.senai.backend.rental_eventos.controllers;

import com.senai.backend.rental_eventos.models.Equipamento;
import com.senai.backend.rental_eventos.services.EquipamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
@CrossOrigin(origins = "*")
public class EquipamentoController {

    private final EquipamentoService equipamentoService;

    public EquipamentoController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    @GetMapping
    public ResponseEntity<List<Equipamento>> listarTodos() {

        return ResponseEntity.ok(
                equipamentoService.listarTodos()
        );
    }

    @GetMapping("/ordenados")
    public ResponseEntity<List<Equipamento>> listarOrdenados() {

        return ResponseEntity.ok(
                equipamentoService.listarOrdenados()
        );
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Equipamento>> buscarPorNome(
            @RequestParam String nome
    ) {

        return ResponseEntity.ok(
                equipamentoService.buscarPorNome(nome)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipamento> buscarPorId(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(
                equipamentoService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Equipamento> cadastrar(
            @RequestBody Equipamento equipamento
    ) {

        return ResponseEntity.ok(
                equipamentoService.salvar(equipamento)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipamento> atualizar(
            @PathVariable Integer id,
            @RequestBody Equipamento equipamento
    ) {

        return ResponseEntity.ok(
                equipamentoService.atualizar(id, equipamento)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Integer id
    ) {

        equipamentoService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/estoque-critico")
    public ResponseEntity<Boolean> verificarEstoqueCritico(
            @PathVariable Integer id
    ) {

        Equipamento equipamento =
                equipamentoService.buscarPorId(id);

        return ResponseEntity.ok(
                equipamentoService.estoqueCritico(equipamento)
        );
    }
}