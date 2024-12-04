package com.example.Cadastrar_Tarefa.controller;

import com.example.Cadastrar_Tarefa.entity.Tarefa;
import com.example.Cadastrar_Tarefa.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    // Método para cadastrar uma tarefa
    @PostMapping("/cadastrar")
    public ResponseEntity<Tarefa> cadastrarTarefa(@RequestBody Tarefa tarefa) {
        try {

            // Salva a tarefa e retorna a resposta com o status 201 (Criado)
            Tarefa tarefaCadastrada = tarefaRepository.save(tarefa);
            return ResponseEntity.status(HttpStatus.CREATED).body(tarefaCadastrada);
        } catch (Exception e) {
            // Caso ocorra algum erro no servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
