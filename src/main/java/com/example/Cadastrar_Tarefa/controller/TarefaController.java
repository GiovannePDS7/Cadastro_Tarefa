package com.example.Cadastrar_Tarefa.controller;

import com.example.Cadastrar_Tarefa.dto.TarefaDTO;
import com.example.Cadastrar_Tarefa.dto.TarefaRespostaDTO;
import com.example.Cadastrar_Tarefa.entity.Evento;
import com.example.Cadastrar_Tarefa.entity.Tarefa;
import com.example.Cadastrar_Tarefa.repository.EventoRepository;
import com.example.Cadastrar_Tarefa.repository.TarefaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarTarefa(@RequestBody TarefaDTO tarefaDTO) {
        if (tarefaDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados da tarefa inválidos.");
        }

        // Encontrar o evento no banco de dados usando o idEvento recebido no TarefaDTO
        Evento evento = eventoRepository.findById(tarefaDTO.getIdEvento())
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado com ID: " + tarefaDTO.getIdEvento()));

        // Criar a tarefa a partir do DTO
        Tarefa tarefa = new Tarefa();
        tarefa.setNomeTarefa(tarefaDTO.getNomeTarefa());
        tarefa.setFornecedor(tarefaDTO.getFornecedor());
        tarefa.setValor(tarefaDTO.getValor());
        tarefa.setEvento(evento);  // Atribuindo o evento encontrado pelo ID

        try {
            // Salvar a tarefa no banco de dados
            Tarefa tarefaCadastrada = tarefaRepository.save(tarefa);

            // Criando o DTO de resposta
            TarefaRespostaDTO respostaDTO = new TarefaRespostaDTO();
            respostaDTO.setIdTarefa(tarefaCadastrada.getIdTarefa());
            respostaDTO.setNomeTarefa(tarefaCadastrada.getNomeTarefa());
            respostaDTO.setFornecedor(tarefaCadastrada.getFornecedor());
            respostaDTO.setValor(tarefaCadastrada.getValor());
            respostaDTO.setIdEvento(tarefaCadastrada.getEvento().getIdEvento());  // Incluindo apenas o ID do evento

            // Retornar a tarefa cadastrada com o ID do evento
            return ResponseEntity.status(HttpStatus.CREATED).body(respostaDTO);
        } catch (Exception e) {
            System.err.println("Erro ao salvar tarefa: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar a tarefa");
        }
    }
    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<?> listarTarefasPorEvento(@PathVariable Long idEvento) {
        try {
            // Busca as tarefas no repositório
            List<Tarefa> tarefas = tarefaRepository.findByEventoIdEvento(idEvento);

            // Converte as tarefas para TarefaRespostaDTO
            List<TarefaRespostaDTO> tarefaRespostaDTOs = tarefas.stream()
                    .map(t -> {
                        TarefaRespostaDTO dto = new TarefaRespostaDTO();
                        dto.setIdTarefa(t.getIdTarefa());
                        dto.setNomeTarefa(t.getNomeTarefa());
                        dto.setFornecedor(t.getFornecedor());
                        dto.setValor(t.getValor());
                        dto.setIdEvento(t.getEvento().getIdEvento());  // Aqui, pegamos o ID do evento
                        return dto;
                    })
                    .collect(Collectors.toList());

            // Retorna a resposta com o status 200 e a lista de TarefaRespostaDTO
            return ResponseEntity.ok(tarefaRespostaDTOs);
        } catch (Exception e) {
            // Em caso de erro, retorna o status 500 com a mensagem de erro
            return ResponseEntity.status(500).body("Erro ao buscar tarefas: " + e.getMessage());
        }
    }




}
