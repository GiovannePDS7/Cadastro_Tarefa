package com.example.Cadastrar_Tarefa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TarefaDTO {
    private String nomeTarefa;
    private String fornecedor;
    private BigDecimal valor;
    private Long idEvento;
}
