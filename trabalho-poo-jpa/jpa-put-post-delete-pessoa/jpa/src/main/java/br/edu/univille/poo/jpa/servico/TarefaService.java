package br.edu.univille.poo.jpa.servico;

import br.edu.univille.poo.jpa.entidade.Tarefa;
import br.edu.univille.poo.jpa.repositorio.TarefaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public Tarefa salvar(@Valid Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> buscarPorId(Long id) {
        return tarefaRepository.findById(id);
    }

    public Tarefa atualizar(Long id, @Valid Tarefa novaTarefa) {
        Tarefa tarefaExistente = buscarPorId(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        if (tarefaExistente.isFinalizado()) {
            throw new RuntimeException("Tarefa finalizada não pode ser modificada");
        }
        tarefaExistente.setTitulo(novaTarefa.getTitulo());
        tarefaExistente.setDescricao(novaTarefa.getDescricao());
        tarefaExistente.setDataPrevistaFinalizacao(novaTarefa.getDataPrevistaFinalizacao());
        tarefaExistente.setDataFinalizacao(novaTarefa.getDataFinalizacao());
        tarefaExistente.setFinalizado(novaTarefa.isFinalizado());
        return tarefaRepository.save(tarefaExistente);
    }

    public void deletar(Long id) {
        Tarefa tarefa = buscarPorId(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        if (tarefa.isFinalizado()) {
            throw new RuntimeException("Tarefa finalizada não pode ser excluída");
        }
        tarefaRepository.deleteById(id);
    }

    public List<Tarefa> listarNaoFinalizadas() {
        return tarefaRepository.findByFinalizado(false);
    }

    public List<Tarefa> listarFinalizadas() {
        return tarefaRepository.findByFinalizado(true);
    }

    public List<Tarefa> listarAtrasadas() {
        return tarefaRepository.findByFinalizadoFalseAndDataPrevistaFinalizacaoBefore(LocalDate.now());
    }

    public List<Tarefa> listarNaoFinalizadasEntreDatas(LocalDate startDate, LocalDate endDate) {
        return tarefaRepository.findByFinalizadoFalseAndDataPrevistaFinalizacaoBetween(startDate, endDate);
    }

    public Tarefa finalizarTarefa(Long id) {
        Tarefa tarefa = buscarPorId(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        if (tarefa.isFinalizado()) {
            throw new RuntimeException("Tarefa já finalizada");
        }
        tarefa.setFinalizado(true);
        tarefa.setDataFinalizacao(LocalDate.now());
        return tarefaRepository.save(tarefa);
    }
}

