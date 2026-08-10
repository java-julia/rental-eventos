package com.senai.backend.rental_eventos.services;

import com.senai.backend.rental_eventos.models.Equipamento;
import com.senai.backend.rental_eventos.repositories.EquipamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }

    public List<Equipamento> listarTodos() {
        return equipamentoRepository.findAll();
    }

    public List<Equipamento> listarOrdenados() {
        return equipamentoRepository.findAllByOrderByNomeAsc();
    }

    public Equipamento buscarPorId(Integer id) {
        return equipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado."));
    }

    public List<Equipamento> buscarPorNome(String nome) {
        return equipamentoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Equipamento salvar(Equipamento equipamento) {

        if (equipamento.getQuantidadeDisponivel() == null) {
            equipamento.setQuantidadeDisponivel(0);
        }

        if (equipamento.getQuantidadeMinima() == null) {
            equipamento.setQuantidadeMinima(1);
        }

        if (equipamento.getAtivo() == null) {
            equipamento.setAtivo(true);
        }

        return equipamentoRepository.save(equipamento);
    }

    public Equipamento atualizar(Integer id, Equipamento equipamentoAtualizado) {

        Equipamento equipamento = buscarPorId(id);

        equipamento.setNome(equipamentoAtualizado.getNome());
        equipamento.setMarca(equipamentoAtualizado.getMarca());
        equipamento.setModelo(equipamentoAtualizado.getModelo());
        equipamento.setCategoria(equipamentoAtualizado.getCategoria());
        equipamento.setPotencia(equipamentoAtualizado.getPotencia());
        equipamento.setMaterial(equipamentoAtualizado.getMaterial());
        equipamento.setPeso(equipamentoAtualizado.getPeso());
        equipamento.setDimensoes(equipamentoAtualizado.getDimensoes());
        equipamento.setCor(equipamentoAtualizado.getCor());
        equipamento.setQuantidadeMinima(equipamentoAtualizado.getQuantidadeMinima());
        equipamento.setAtivo(equipamentoAtualizado.getAtivo());

        return equipamentoRepository.save(equipamento);
    }

    public void excluir(Integer id) {

        Equipamento equipamento = buscarPorId(id);

        equipamentoRepository.delete(equipamento);
    }

    public boolean estoqueCritico(Equipamento equipamento) {

        return equipamento.getQuantidadeDisponivel()
                <= equipamento.getQuantidadeMinima();
    }
}