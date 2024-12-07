package com.example.Cadastrar_Tarefa.repository;

import com.example.Cadastrar_Tarefa.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository  extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByEventoIdEvento(Long idEvento);

}
