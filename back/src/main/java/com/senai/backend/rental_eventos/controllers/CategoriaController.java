package com.senai.backend.rental_eventos.controllers;

import com.senai.backend.rental_eventos.models.Categoria;
import com.senai.backend.rental_eventos.services.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodos() {
        return ResponseEntity.ok(categoriaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                categoriaService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<Categoria> cadastrar(
            @RequestBody Categoria categoria
    ) {
        return ResponseEntity.ok(
                categoriaService.salvar(categoria)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizar(
            @PathVariable Integer id,
            @RequestBody Categoria categoria
    ) {
        return ResponseEntity.ok(
                categoriaService.atualizar(id, categoria)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Integer id
    ) {

        categoriaService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}