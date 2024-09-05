package br.edu.univille.poo.jpa.controller;

import br.edu.univille.poo.jpa.entidade.Tarefa;
import br.edu.univille.poo.jpa.servico.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaRestController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<Tarefa> inserir(@RequestBody Tarefa tarefa) {
        return new ResponseEntity<>(tarefaService.salvar(tarefa), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTodas() {
        return ResponseEntity.ok(tarefaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        return tarefaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        try {
            return ResponseEntity.ok(tarefaService.atualizar(id, tarefa));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            tarefaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/nao-finalizadas")
    public ResponseEntity<List<Tarefa>> listarNaoFinalizadas() {
        return ResponseEntity.ok(tarefaService.listarNaoFinalizadas());
    }

    @GetMapping("/finalizadas")
    public ResponseEntity<List<Tarefa>> listarFinalizadas() {
        return ResponseEntity.ok(tarefaService.listarFinalizadas());
    }

    @GetMapping("/atrasadas")
    public ResponseEntity<List<Tarefa>> listarAtrasadas() {
        return ResponseEntity.ok(tarefaService.listarAtrasadas());
    }

    @GetMapping("/nao-finalizadas/entre-datas")
    public ResponseEntity<List<Tarefa>> listarNaoFinalizadasEntreDatas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(tarefaService.listarNaoFinalizadasEntreDatas(startDate, endDate));
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<Tarefa> finalizar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(tarefaService.finalizarTarefa(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
