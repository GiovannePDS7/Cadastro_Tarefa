package com.example.Cadastrar_Tarefa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TarefaRespostaDTO {
    private Long idTarefa;
    private String nomeTarefa;
    private String fornecedor;
    private BigDecimal valor;
    private Long idEvento;  // Apenas o ID do evento
}
