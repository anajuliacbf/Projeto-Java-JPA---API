package br.edu.univille.poo.jpa.repositorio;

import br.edu.univille.poo.jpa.entidade.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByFinalizado(boolean finalizado);
    List<Tarefa> findByFinalizadoFalseAndDataPrevistaFinalizacaoBetween(LocalDate startDate, LocalDate endDate);
    List<Tarefa> findByFinalizadoFalseAndDataPrevistaFinalizacaoBefore(LocalDate date);
}