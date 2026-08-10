package com.senai.backend.rental_eventos.services;

import com.senai.backend.rental_eventos.models.Equipamento;
import com.senai.backend.rental_eventos.models.Movimentacao;
import com.senai.backend.rental_eventos.models.Usuario;
import com.senai.backend.rental_eventos.repositories.EquipamentoRepository;
import com.senai.backend.rental_eventos.repositories.MovimentacaoRepository;
import com.senai.backend.rental_eventos.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public MovimentacaoService(
            MovimentacaoRepository movimentacaoRepository,
            EquipamentoRepository equipamentoRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.equipamentoRepository = equipamentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Movimentacao realizarMovimentacao(
            Integer equipamentoId,
            Integer usuarioId,
            String tipoMovimentacao,
            Integer quantidade,
            LocalDateTime dataMovimentacao,
            String observacao
    ) {

        if (quantidade == null || quantidade <= 0) {
            throw new RuntimeException(
                    "A quantidade deve ser maior que zero."
            );
        }

        if (tipoMovimentacao == null ||
                (!tipoMovimentacao.equalsIgnoreCase("ENTRADA")
                        && !tipoMovimentacao.equalsIgnoreCase("SAIDA"))) {

            throw new RuntimeException(
                    "O tipo de movimentação deve ser ENTRADA ou SAIDA."
            );
        }

        Equipamento equipamento = equipamentoRepository.findById(equipamentoId)
                .orElseThrow(() ->
                        new RuntimeException("Equipamento não encontrado.")
                );

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado.")
                );

        if (dataMovimentacao == null) {
            dataMovimentacao = LocalDateTime.now();
        }

        if (tipoMovimentacao.equalsIgnoreCase("ENTRADA")) {

            equipamento.setQuantidadeDisponivel(
                    equipamento.getQuantidadeDisponivel() + quantidade
            );

        } else {

            if (quantidade > equipamento.getQuantidadeDisponivel()) {
                throw new RuntimeException(
                        "Estoque insuficiente para realizar a saída."
                );
            }

            equipamento.setQuantidadeDisponivel(
                    equipamento.getQuantidadeDisponivel() - quantidade
            );
        }

        equipamentoRepository.save(equipamento);

        Movimentacao movimentacao = new Movimentacao();

        movimentacao.setEquipamento(equipamento);
        movimentacao.setUsuario(usuario);
        movimentacao.setDataMovimentacao(dataMovimentacao);
        movimentacao.setTipoMovimentacao(
                tipoMovimentacao.toUpperCase()
        );
        movimentacao.setQuantidade(quantidade);
        movimentacao.setObservacao(observacao);

        return movimentacaoRepository.save(movimentacao);
    }

    public List<Movimentacao> listarTodas() {

        return movimentacaoRepository
                .findAllByOrderByDataMovimentacaoDesc();
    }

    public List<Movimentacao> listarPorEquipamento(Integer equipamentoId) {

        return movimentacaoRepository
                .findByEquipamentoIdOrderByDataMovimentacaoDesc(
                        equipamentoId
                );
    }

    public String verificarEstoque(Integer equipamentoId) {

        Equipamento equipamento = equipamentoRepository.findById(equipamentoId)
                .orElseThrow(() ->
                        new RuntimeException("Equipamento não encontrado.")
                );

        if (equipamento.getQuantidadeDisponivel()
                <= equipamento.getQuantidadeMinima()) {

            return "ALERTA: estoque abaixo ou igual ao mínimo configurado.";
        }

        return "Estoque normal.";
    }
}
